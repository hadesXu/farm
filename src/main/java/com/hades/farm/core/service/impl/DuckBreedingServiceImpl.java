package com.hades.farm.core.service.impl;

import com.hades.farm.core.data.dto.requestDto.BreedingRequestDto;
import com.hades.farm.core.data.entity.TDuckBreeding;
import com.hades.farm.core.data.entity.TDuckWarehouse;
import com.hades.farm.core.data.entity.TNotice;
import com.hades.farm.core.data.mapper.TDuckBreedingMapper;
import com.hades.farm.core.data.mapper.TDuckWarehouseMapper;
import com.hades.farm.core.data.mapper.TNoticeMapper;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.core.service.DuckBreedingService;
import com.hades.farm.core.service.WareHouseService;
import com.hades.farm.enums.NoticeType;
import com.hades.farm.result.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhengzl on 2018/3/10.
 */
@Service
public class DuckBreedingServiceImpl implements DuckBreedingService {
    @Autowired
    private TDuckWarehouseMapper tDuckWarehouseMapper;
    @Autowired
    private TDuckBreedingMapper tDuckBreedingMapper;
    @Autowired
    private TNoticeMapper tNoticeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean breeding(BreedingRequestDto requestDto) throws BizException{
        int updateCount = 0;
        TDuckWarehouse duckWarehouse = tDuckWarehouseMapper.selectByUserId(requestDto.getUserId());
        if(duckWarehouse == null){
            throw new BizException(ErrorCode.DUCK_NO_ENOUGH);
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
        updateCount = tDuckWarehouseMapper.updateDuckWareHouseBreedingDuck(requestDto);
        if(updateCount !=1){
            throw new BizException(ErrorCode.UPDATE_ERR);
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
