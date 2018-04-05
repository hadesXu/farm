package com.hades.farm.core.service;

import com.hades.farm.core.data.entity.*;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.enums.AcctOpreType;
import com.hades.farm.result.Result;

import java.util.List;

/**
 * Created by xiaoxu on 2018/3/24.
 */
public interface AccountService {

    /**
     * 获取用户账户
     *
     * @param userId
     * @return
     */
    Result<TAccountTicket> getAccount(long userId);

    /**
     * 获取账户积分
     *
     * @param userId
     * @return
     */
    Result<TAccountIntegral> getAccountIntegral(long userId);

    /**
     * 增减用户余额
     *
     * @param accountTicket
     * @param type
     * @return
     */
    Result<TAccountTicket> updateAccount(TAccountTicket accountTicket, AcctOpreType type) throws BizException;


    /**
     * 获取账户积分记录
     *
     * @param userId
     * @param page
     * @param num
     * @return
     */
    Result<List<TAccountIntegralFlow>> findIntegralRecord(long userId, int page, int num);

    /**
     * 获取佣金记录
     *
     * @param userId
     * @param page
     * @param num
     * @return
     */
    Result<List<TAccountTicketFlow>> findTicketRecord(long userId, int page, int num);

    /**
     * 获取交易记录
     *
     * @param userId
     * @param page
     * @param num
     * @return
     */
    Result<List<TAccountTicketFlow>> findAccountRecord(long userId, int page, int num);

}
