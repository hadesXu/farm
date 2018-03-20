package com.hades.farm.core.service;

import com.hades.farm.core.exception.BizException;
import com.hades.farm.result.Result;

/**
 * Created by xiaoxu on 2018/3/18.
 */
public interface CashBackService {


    boolean cashBack(long userId) throws BizException;

}
