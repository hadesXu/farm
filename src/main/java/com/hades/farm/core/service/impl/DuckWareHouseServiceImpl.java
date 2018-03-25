package com.hades.farm.core.service.impl;

import com.hades.farm.core.data.entity.TDuckWarehouse;
import com.hades.farm.core.data.mapper.TDuckWarehouseMapper;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.core.service.WareHouseService;
import com.hades.farm.result.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhengzl on 2018/3/10.
 */
@Service("duckWareHouseServiceImpl")
public class DuckWareHouseServiceImpl implements WareHouseService {
    @Autowired
    private TDuckWarehouseMapper tDuckWarehouseMapper;
    @Override
    public void addWareHouse(long userId) throws BizException{
        //添加仓库记录
        TDuckWarehouse duckWarehouse = new TDuckWarehouse();
        duckWarehouse.setUserId(userId);
        duckWarehouse.setDuck(0);
        duckWarehouse.setDuckDoing(0);
        duckWarehouse.setEgg(0);
        duckWarehouse.setEggFreeze(0);
        duckWarehouse.setEggHarvest(0);
        duckWarehouse.setIfHarvest(2);
        duckWarehouse.setIfSteal(2);
        duckWarehouse.setAllSell(0);
        duckWarehouse.setAllProfit(new BigDecimal("0"));
        duckWarehouse.setAllIntegral(new BigDecimal("0"));
        duckWarehouse.setFood(0);
        duckWarehouse.setAddTime(new Date());
        duckWarehouse.setUpdateTime(new Date());
        int updateCount = tDuckWarehouseMapper.insertSelective(duckWarehouse);
        if(updateCount !=1){
            throw new BizException(ErrorCode.ADD_ERR);
        }
    }

    public TDuckWarehouse queryDuckWareHouse(long userId){
        return tDuckWarehouseMapper.selectByUserId(userId);
    }
}
