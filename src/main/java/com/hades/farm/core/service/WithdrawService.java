package com.hades.farm.core.service;

import com.hades.farm.api.view.request.WithdrawRequest;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.result.Result;

/**
 * Created by xiaoxu on 2018/4/6.
 */
public interface WithdrawService {

    /**
     * 提现
     *
     * @param request
     * @return
     */
    void withdraw(WithdrawRequest request) throws BizException;

}
