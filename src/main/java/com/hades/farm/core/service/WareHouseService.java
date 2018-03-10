package com.hades.farm.core.service;

import com.hades.farm.core.exception.BizException;

/**
 * Created by zhengzl on 2018/3/10.
 */
public interface WareHouseService {
    public void addWareHouse(long userId) throws BizException;
}
