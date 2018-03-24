package com.hades.farm.core.service.impl;

import com.hades.farm.core.data.entity.TAccountTicket;
import com.hades.farm.core.data.entity.TAccountTicketFlow;
import com.hades.farm.core.data.entity.TOrders;
import com.hades.farm.core.data.mapper.TAccountTicketFlowMapper;
import com.hades.farm.core.data.mapper.TAccountTicketMapper;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.core.service.AccountService;
import com.hades.farm.enums.AcctOpreType;
import com.hades.farm.result.ErrorCode;
import com.hades.farm.result.Result;
import com.hades.farm.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

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


    @Override

    public Result<TAccountTicket> getAccount(long userId) {
        Result<TAccountTicket> result = Result.newResult();
        result.setData(tAccountTicketMapper.queryAccountByUserId(userId));
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
