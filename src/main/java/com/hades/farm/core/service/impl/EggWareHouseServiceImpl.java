package com.hades.farm.core.service.impl;

import com.hades.farm.api.view.response.StealModel;
import com.hades.farm.core.data.entity.TEggWarehouse;
import com.hades.farm.core.data.mapper.TEggWarehouseMapper;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.result.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by zhengzl on 2018/3/10.
 */
@Service
public class EggWareHouseServiceImpl{
    @Autowired
    private TEggWarehouseMapper tEggWarehouseMapper;

    public void addWareHouse(long userId) throws BizException{
        TEggWarehouse eggWarehouse = new TEggWarehouse();
        eggWarehouse.setUserId(userId);
        eggWarehouse.setEgg(0);
        eggWarehouse.setEggDoing(0);
        eggWarehouse.setDuck(0);
        eggWarehouse.setDuckFreeze(0);
        eggWarehouse.setDuckHarvest(0);
        eggWarehouse.setIfHot(2);
        eggWarehouse.setIfHarvest(2);
        eggWarehouse.setIfSteal(2);
        eggWarehouse.setAllSell(0);
        eggWarehouse.setAllProfit(new BigDecimal("0"));
        eggWarehouse.setAllIntegral(new BigDecimal("0"));
        eggWarehouse.setAddTime(new Date());
        eggWarehouse.setUpdateTime(new Date());
        int updateCount = tEggWarehouseMapper.insertSelective(eggWarehouse);
        if(updateCount !=1){
            throw new BizException(ErrorCode.ADD_ERR);
        }
    }

    public TEggWarehouse queryEggWareHouse(long userId){
        return tEggWarehouseMapper.selectByUserId(userId);
    }

    public List<StealModel> queryCanStealList(Long userId,int offSet,int pageSize){
       return tEggWarehouseMapper.queryCanStealList(userId,offSet,pageSize);
    }
}
