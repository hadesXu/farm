package com.hades.farm.core.service;

import com.hades.farm.api.view.response.MyWarehouseModel;

/**
 * Created by zhengzl on 2018/4/5.
 */
public interface WarehouseQueryService {
    MyWarehouseModel mywarehouse(long userId);
}
