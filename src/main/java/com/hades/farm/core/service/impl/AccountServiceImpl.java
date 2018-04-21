package com.hades.farm.core.service.impl;

import com.hades.farm.core.data.entity.*;
import com.hades.farm.core.data.mapper.*;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.core.service.AccountService;
import com.hades.farm.enums.AcctOpreType;
import com.hades.farm.result.ErrorCode;
import com.hades.farm.result.Result;
import com.hades.farm.utils.Constant;
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
