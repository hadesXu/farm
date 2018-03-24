package com.hades.farm.core.service;

import com.hades.farm.core.data.entity.TAccountTicket;
import com.hades.farm.core.data.entity.TOrders;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.enums.AcctOpreType;
import com.hades.farm.result.Result;

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
     * 增减用户余额
     *
     * @param accountTicket
     * @param type
     * @return
     */
    Result<TAccountTicket> updateAccount(TAccountTicket accountTicket, AcctOpreType type) throws BizException;
}
