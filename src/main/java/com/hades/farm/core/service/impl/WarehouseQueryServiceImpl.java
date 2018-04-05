package com.hades.farm.core.service.impl;

import com.hades.farm.api.view.response.MyWarehouseModel;
import com.hades.farm.core.data.mapper.TDuckWarehouseMapper;
import com.hades.farm.core.data.mapper.TEggWarehouseMapper;
import com.hades.farm.core.service.WarehouseQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhengzl on 2018/4/5.
 */
@Service
public class WarehouseQueryServiceImpl implements WarehouseQueryService {
    @Autowired
    private TDuckWarehouseMapper duckWarehouseMapper;
    @Autowired
    private TEggWarehouseMapper eggWarehouseMapper;
    @Override
    public MyWarehouseModel mywarehouse(long userId){
        MyWarehouseModel myWarehouseModel = new MyWarehouseModel();
        myWarehouseModel.setDuckWarehouse(duckWarehouseMapper.selectByUserId(userId));
        myWarehouseModel.setEggWarehouse(eggWarehouseMapper.selectByUserId(userId));
        return myWarehouseModel;
    }
}
