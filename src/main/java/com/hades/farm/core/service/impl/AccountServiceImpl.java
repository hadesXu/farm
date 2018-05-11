package com.hades.farm.core.service.impl;

import com.hades.farm.core.data.entity.*;
import com.hades.farm.core.data.mapper.*;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.core.service.AccountService;
import com.hades.farm.enums.*;
import com.hades.farm.result.ErrorCode;
import com.hades.farm.result.Result;
import com.hades.farm.utils.Constant;
import com.hades.farm.utils.SystemUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by xiaoxu on 2018/3/24.
 */
@Service
public class AccountServiceImpl implements AccountService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Object updateLock = new Object();

    @Resource
    private TAccountTicketMapper tAccountTicketMapper;
    @Resource
    private TAccountTicketFlowMapper tAccountTicketFlowMapper;
    @Resource
    private TAccountIntegralFlowMapper tAccountIntegralFlowMapper;
    @Resource
    private TAccountIntegralMapper tAccountIntegralMapper;
    @Resource
    private TWithdrawMapper tWithdrawMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private TDuckWarehouseMapper tDuckWarehouseMapper;
    @Resource
    private TEggWarehouseMapper tEggWarehouseMapper;
    @Resource
    private TNoticeMapper tNoticeMapper;

    @Override

    public Result<TAccountTicket> getAccount(long userId) {
        Result<TAccountTicket> result = Result.newResult();
        TAccountTicket tAccountTicket = tAccountTicketMapper.queryAccountByUserId(userId);
        if (tAccountTicket == null) {
            result.addError(ErrorCode.SYSTEM_ERROR);
            return result;
        }
        result.setData(tAccountTicket);
        return result;
    }

    @Override
    public Result<TAccountIntegral> getAccountIntegral(long userId) {
        Result<TAccountIntegral> result = Result.newResult();
        TAccountIntegral tAccountIntegral = tAccountIntegralMapper.queryByUserId(userId);
        if (tAccountIntegral == null) {
            result.addError(ErrorCode.SYSTEM_ERROR);
            return result;
        }
        result.setData(tAccountIntegral);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<TAccountTicket> updateAccount(TAccountTicket accountTicket, AcctOpreType type) throws BizException {
        Result<TAccountTicket> result = Result.newResult();
        long userId = accountTicket.getUserId();
        if (userId <= Constant.DEFAULT_ID) {
            result.addError(ErrorCode.SYSTEM_ERROR);
            return result;
        }
        synchronized (updateLock) {
            TAccountTicket oldAccount = tAccountTicketMapper.queryAccountByUserId(accountTicket.getUserId());
            TAccountTicket resultAccount = new TAccountTicket();
            BigDecimal oldBalance = accountTicket.getBalance();
            if (oldAccount != null) {
                oldBalance = oldAccount.getBalance();
                BigDecimal resultBalance = calculate(oldAccount.getBalance(), accountTicket.getBalance());
                BigDecimal resultAccRecharge = calculate(oldAccount.getAccRecharge(), accountTicket.getAccRecharge());
                BigDecimal resultFrozen = calculate(oldAccount.getFrozen(), accountTicket.getFrozen());
                BigDecimal resultAccWithdraw = calculate(oldAccount.getAccWithdraw(), accountTicket.getAccWithdraw());
                BigDecimal resultAccProfit = calculate(oldAccount.getAccProfit(), accountTicket.getAccProfit());
                BigDecimal resultAccCommission = calculate(oldAccount.getAccCommission(), accountTicket.getAccCommission());
                if (compareTo(resultBalance) || compareTo(resultAccRecharge) ||
                        compareTo(resultFrozen) || compareTo(resultAccWithdraw) || compareTo(resultAccProfit) || compareTo(resultAccCommission)) {
                    result.addError(ErrorCode.BALANCE_NOT_ERR);
                    return result;
                }
                resultAccount.setBalance(resultBalance);
                resultAccount.setAccRecharge(resultAccRecharge);
                resultAccount.setFrozen(resultFrozen);
                resultAccount.setAccWithdraw(resultAccWithdraw);
                resultAccount.setAccProfit(resultAccProfit);
                resultAccount.setAccCommission(resultAccCommission);
            } else {
                resultAccount.setBalance(accountTicket.getBalance());
                resultAccount.setAccRecharge(accountTicket.getAccRecharge());
                resultAccount.setFrozen(accountTicket.getFrozen());
                resultAccount.setAccWithdraw(accountTicket.getAccWithdraw());
                resultAccount.setAccProfit(accountTicket.getAccProfit());
                resultAccount.setAccCommission(accountTicket.getAccCommission());
            }
            int updCount = tAccountTicketMapper.incUserAccount(accountTicket);
            if (updCount == Constant.NUMBER_ZERO) {
                result.addError(ErrorCode.UPDATE_ERR);
                return result;
            }
            if (!compareTo(accountTicket.getBalance())) {
                TAccountTicketFlow accountTicketFlow = new TAccountTicketFlow();
                accountTicketFlow.setUserId(accountTicket.getUserId());
                accountTicketFlow.setType(type.getType());
                accountTicketFlow.setAmount(accountTicket.getBalance());
                accountTicketFlow.setAmountBefore(oldBalance);
                accountTicketFlow.setAmountAfter(resultAccount.getBalance());
                accountTicketFlow.setRemarks(type.getDesc() + "操作金额" + accountTicket.getBalance());
                accountTicketFlow.setAddTime(new Date());
                int updateCount = tAccountTicketFlowMapper.insertSelective(accountTicketFlow);
                if (updateCount != 1) {
                    throw new BizException(ErrorCode.ADD_ERR);
                }
            }
        }
        return result;
    }

    @Override
    public Result<List<TAccountIntegralFlow>> findIntegralRecord(long userId, int page, int num) {
        Result<List<TAccountIntegralFlow>> result = Result.newResult();
        int offset = (page - 1) * num;
        List<TAccountIntegralFlow> integrals = tAccountIntegralFlowMapper.findAccountRecord(userId, offset, num);
        if (CollectionUtils.isNotEmpty(integrals)) {
            result.setData(integrals);
        }
        return result;
    }

    @Override
    public Result<List<TAccountTicketFlow>> findTicketRecord(long userId, int page, int num) {
        Result<List<TAccountTicketFlow>> result = Result.newResult();
        int offset = (page - 1) * num;
        List<TAccountTicketFlow> flows = tAccountTicketFlowMapper.findTicketRecord(userId, offset, num);
        if (CollectionUtils.isNotEmpty(flows)) {
            result.setData(flows);
        }
        return result;
    }

    @Override
    public Result<List<TAccountTicketFlow>> findAccountRecord(long userId, int page, int num) {
        Result<List<TAccountTicketFlow>> result = Result.newResult();
        int offset = (page - 1) * num;
        List<TAccountTicketFlow> flows = tAccountTicketFlowMapper.findAccountRecord(userId, offset, num);
        if (CollectionUtils.isNotEmpty(flows)) {
            result.setData(flows);
        }
        return result;
    }

    @Override
    public Result<List<TWithdraw>> findTWithdraw(long userId, int page, int num) {
        Result<List<TWithdraw>> result = Result.newResult();
        int offset = (page - 1) * num;
        List<TWithdraw> withdraws = tWithdrawMapper.findWithdraw(userId, offset, num);
        if (CollectionUtils.isNotEmpty(withdraws)) {
            result.setData(withdraws);
        }
        return result;
    }

    @Override
    public void integralChange(long userId, BigDecimal num) throws BizException {
        if (num.compareTo(BigDecimal.ZERO) <= Constant.NUMBER_ZERO) {
            throw new BizException(ErrorCode.ARGUMENTS_ERROR);
        }
        if (num.compareTo(BigDecimal.TEN) < Constant.NUMBER_ZERO) {
            throw new BizException(ErrorCode.INTEGRAL_ERROR);
        }
        TAccountTicket tAccountTicket = tAccountTicketMapper.queryAccountByUserId(userId);
        if (tAccountTicket.getBalance().compareTo(num) < Constant.NUMBER_ZERO) {
            throw new BizException(ErrorCode.ARGUMENTS_ERROR2);
        }
        User user = userMapper.getUserById(userId);
        if (user == null) {
            throw new BizException(ErrorCode.USER_NOT_EXIST);
        }
        int uRes = tAccountTicketMapper.integral(userId, num);
        if (uRes == Constant.NUMBER_ZERO) {
            throw new BizException(ErrorCode.UPDATE_ERR);
        }

        //添加积分
        TAccountIntegral sellAccountIntegral = tAccountIntegralMapper.queryByUserId(userId);
        BigDecimal integralBefore = new BigDecimal("0");
        BigDecimal integralAfter = new BigDecimal("0");
        int updateCount = 0;
        if (sellAccountIntegral == null) {
            integralAfter = num;
            sellAccountIntegral = new TAccountIntegral();
            sellAccountIntegral.setUserId(userId);
            sellAccountIntegral.setBalance(num);
            sellAccountIntegral.setAccConsume(new BigDecimal("0"));
            sellAccountIntegral.setAddTime(new Date());
            sellAccountIntegral.setUpdateTime(new Date());
            sellAccountIntegral.setAccGain(num);
            updateCount = tAccountIntegralMapper.insertSelective(sellAccountIntegral);
        } else {
            integralBefore = sellAccountIntegral.getBalance();
            integralAfter = integralBefore.add(num);
            //更新
            TAccountIntegral updateIntegral = new TAccountIntegral();
            updateIntegral.setAccGain(num);
            updateIntegral.setBalance(num);
            updateIntegral.setUserId(userId);

            updateCount = tAccountIntegralMapper.updateIntegralByUserId(updateIntegral);
        }
        if (updateCount < 1) {
            throw new BizException(ErrorCode.UPDATE_ERR);
        }
        //添加积分流水
        TAccountIntegralFlow accountIntegralFlow = new TAccountIntegralFlow();
        accountIntegralFlow.setUserId(userId);
        accountIntegralFlow.setType(String.valueOf(IntegralType.THREE.getType()));
        accountIntegralFlow.setAmount(num);
        accountIntegralFlow.setAmountBefore(integralBefore);
        accountIntegralFlow.setAmountAfter(integralAfter);
        accountIntegralFlow.setAddTime(new Date());
        accountIntegralFlow.setUpdateTime(new Date());
        accountIntegralFlow.setRemarks("菜票兑换菜币，获得" + num + "菜币");
        updateCount = tAccountIntegralFlowMapper.insertSelective(accountIntegralFlow);
        if (updateCount < 1) {
            throw new BizException(ErrorCode.ADD_ERR);
        }
        //新增账户流水表记录
        TAccountTicketFlow accountTicketFlow = new TAccountTicketFlow();
        accountTicketFlow.setUserId(userId);
        accountTicketFlow.setType(AcctOpreType.EXCHANGE_GRADE.getType());
        accountTicketFlow.setAmount(num);
        accountTicketFlow.setAmountBefore(tAccountTicket.getBalance());
        accountTicketFlow.setAmountAfter(tAccountTicket.getBalance().subtract(num));
        accountTicketFlow.setRemarks("兑换菜币：" + num);
        accountTicketFlow.setAddTime(new Date());
        uRes = tAccountTicketFlowMapper.insertSelective(accountTicketFlow);
        if (uRes == Constant.NUMBER_ZERO) {
            throw new BizException(ErrorCode.ADD_ERR);
        }

    }

    @Override
    public void goodsChange(long userId, int type, BigDecimal num) throws BizException {
        if (num.compareTo(BigDecimal.ZERO) <= Constant.NUMBER_ZERO) {
            throw new BizException(ErrorCode.ARGUMENTS_ERROR);
        }
        if (num.compareTo(BigDecimal.TEN) < Constant.NUMBER_ZERO) {
            throw new BizException(ErrorCode.INTEGRAL_ERROR);
        }
        BigDecimal balance = BigDecimal.ZERO;
        int updateCount = 0;
        //更新个人仓库表
        if (GoodsType.EGG.getType() == type) {
            updateCount = tDuckWarehouseMapper.duckChange(userId, num);
            if (updateCount != 1) {
                throw new BizException(ErrorCode.EGG_NO_ENOUGH);
            }
            balance = Constant.EGG_PRICE.multiply(num);
        } else if (GoodsType.DUCK.getType() == type) {
            updateCount = tEggWarehouseMapper.eggChange(userId, num);
            if (updateCount != 1) {
                throw new BizException(ErrorCode.DUCK_NO_ENOUGH);
            }
            balance = Constant.DUCK_PRICE.multiply(num);
        } else {
            throw new BizException(ErrorCode.GOOD_TYPE_ERROR);
        }
        //添加积分
        TAccountIntegral sellAccountIntegral = tAccountIntegralMapper.queryByUserId(userId);
        BigDecimal integralBefore = new BigDecimal("0");
        BigDecimal integralAfter = new BigDecimal("0");
        if (sellAccountIntegral == null) {
            integralAfter = balance;
            sellAccountIntegral = new TAccountIntegral();
            sellAccountIntegral.setUserId(userId);
            sellAccountIntegral.setBalance(balance);
            sellAccountIntegral.setAccConsume(new BigDecimal("0"));
            sellAccountIntegral.setAddTime(new Date());
            sellAccountIntegral.setAccGain(balance);
            sellAccountIntegral.setUpdateTime(new Date());
            updateCount = tAccountIntegralMapper.insertSelective(sellAccountIntegral);
        } else {
            integralBefore = sellAccountIntegral.getBalance();
            integralAfter = integralBefore.add(balance);
            //更新
            TAccountIntegral updateIntegral = new TAccountIntegral();
            updateIntegral.setBalance(balance);
            updateIntegral.setUserId(userId);
            updateIntegral.setAccGain(balance);
            updateCount = tAccountIntegralMapper.updateIntegralByUserId(updateIntegral);
        }
        if (updateCount < 1) {
            throw new BizException(ErrorCode.UPDATE_ERR);
        }
        //添加积分流水
        TAccountIntegralFlow accountIntegralFlow = new TAccountIntegralFlow();
        accountIntegralFlow.setUserId(userId);
        accountIntegralFlow.setType(String.valueOf(IntegralType.FOUR.getType()));
        accountIntegralFlow.setAmount(num);
        accountIntegralFlow.setAmountBefore(integralBefore);
        accountIntegralFlow.setAmountAfter(integralAfter);
        accountIntegralFlow.setAddTime(new Date());
        accountIntegralFlow.setUpdateTime(new Date());
        accountIntegralFlow.setRemarks("商品兑换菜币，获得" + num + "菜币");
        updateCount = tAccountIntegralFlowMapper.insertSelective(accountIntegralFlow);
        if (updateCount < 1) {
            throw new BizException(ErrorCode.ADD_ERR);
        }

        //添加日志记录t_notice
        TNotice tNotice = new TNotice();
        tNotice.setUserId(userId);

        if (GoodsType.EGG.getType() == type) {
            tNotice.setType(NoticeType.EGG_CHANGE.getType());
            tNotice.setRemarks(NoticeType.EGG_CHANGE.getRemarks().replace("num", num + ""));
        } else {
            tNotice.setType(NoticeType.DUCK_CHANGE.getType());
            tNotice.setRemarks(NoticeType.DUCK_CHANGE.getRemarks().replace("num", num + ""));
        }


        tNotice.setAddTime(new Date());
        updateCount = tNoticeMapper.insertSelective(tNotice);
        if (updateCount != 1) {
            throw new BizException(ErrorCode.ADD_ERR);
        }

    }

    private BigDecimal calculate(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }

    private boolean compareTo(BigDecimal a) {
        if (a.compareTo(new BigDecimal(Constant.NUMBER_ZERO)) < Constant.NUMBER_ZERO) {
            return true;
        } else {
            return false;
        }
    }
}
