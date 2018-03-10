package com.hades.farm.core.service.impl;

import com.hades.farm.core.data.dto.requestDto.BreedingRequestDto;
import com.hades.farm.core.data.entity.TDuckBreeding;
import com.hades.farm.core.data.entity.TDuckWarehouse;
import com.hades.farm.core.data.entity.TNotice;
import com.hades.farm.core.data.mapper.TDuckBreedingMapper;
import com.hades.farm.core.data.mapper.TDuckWarehouseMapper;
import com.hades.farm.core.data.mapper.TNoticeMapper;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.enums.NoticeType;
import com.hades.farm.result.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhengzl on 2018/3/10.
 */
@Service
public class DuckBreedingServiceImpl {
    @Autowired
    private TDuckWarehouseMapper tDuckWarehouseMapper;
    @Autowired
    private TDuckBreedingMapper tDuckBreedingMapper;
    @Autowired
    private TNoticeMapper tNoticeMapper;
    @Transactional(rollbackFor = Exception.class)
    public boolean breeding(BreedingRequestDto requestDto) throws BizException{
        int updateCount = 0;
        TDuckWarehouse duckWarehouse = tDuckWarehouseMapper.selectByUserId(requestDto.getUserId());
        if(duckWarehouse == null){
            //添加仓库记录
            duckWarehouse = new TDuckWarehouse();
            duckWarehouse.setUserId(requestDto.getUserId());
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
            duckWarehouse.setFood(new BigDecimal("0"));
            duckWarehouse.setAddTime(new Date());
            duckWarehouse.setUpdateTime(new Date());
            tDuckWarehouseMapper.insertSelective(duckWarehouse);
            updateCount = tDuckWarehouseMapper.insertSelective(duckWarehouse);
            if(updateCount !=1){
                throw new BizException(ErrorCode.ADD_ERR);
            }
        }else if(duckWarehouse.getDuck()<requestDto.getNum()) {
            throw new BizException(ErrorCode.DUCK_NO_ENOUGH);
        }
        // 入库，更新鸭养殖数量，更新鸭仓库
        TDuckBreeding duckBreeding = new TDuckBreeding();
        duckBreeding.setUserId(requestDto.getUserId());
        duckBreeding.setNum(requestDto.getNum());
        duckBreeding.setNumHarvest(0);
        duckBreeding.setDay(0);
        duckBreeding.setAccNoFeed(0);
        duckBreeding.setStatus(1);
        duckBreeding.setIfFeed(2);
        duckBreeding.setAddTime(new Date());
        duckBreeding.setUpdateTime(new Date());
        updateCount = tDuckBreedingMapper.insertSelective(duckBreeding);
        if(updateCount !=1){
            throw new BizException(ErrorCode.ADD_ERR);
        }
        //养鸭notice记录
        TNotice tNotice = new TNotice();
        tNotice.setUserId(requestDto.getUserId());
        tNotice.setType(NoticeType.BREEDING_DUCK.getType());
        tNotice.setRemarks(NoticeType.BREEDING_DUCK.getRemarks().replace("num", requestDto.getNum() + ""));
        tNotice.setAddTime(new Date());
        tNoticeMapper.insertSelective(tNotice);
        return true;
    }
}
