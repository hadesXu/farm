package com.hades.farm.core.service.impl;

import com.hades.farm.core.data.dto.requestDto.BreedingRequestDto;
import com.hades.farm.core.data.entity.TEggBreeding;
import com.hades.farm.core.data.entity.TEggWarehouse;
import com.hades.farm.core.data.mapper.TEggBreedingMapper;
import com.hades.farm.core.data.mapper.TEggWarehouseMapper;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.core.service.EggBreedingService;
import com.hades.farm.result.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhengzl on 2018/3/9.
 */
@Service
public class EggBreedingServiceImpl implements EggBreedingService {
    @Autowired
    private TEggBreedingMapper tEggBreedingMapper;
    @Autowired
    private TEggWarehouseMapper tEggWarehouseMapper;

    /**
     * 孵蛋
     * @param requestDto
     * @return
     * @throws BizException
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean breeding(BreedingRequestDto requestDto) throws BizException {
        //校验仓库中蛋的数量
        TEggWarehouse eggWarehouse = tEggWarehouseMapper.selectByUserId(requestDto.getUserId());
        int updateCount = 0;
        if(eggWarehouse == null){
            //添加记录
            eggWarehouse = new TEggWarehouse();
            eggWarehouse.setUserId(requestDto.getUserId());
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
            updateCount = tEggWarehouseMapper.insertSelective(eggWarehouse);
            if(updateCount !=1){
                throw new BizException(ErrorCode.ADD_ERR);
            }
        }else if(eggWarehouse.getEgg()<requestDto.getNum()){
            throw new BizException(ErrorCode.EGG_NO_ENOUGH);
        }
        //入库，更新蛋养殖数量，更新仓库
        TEggBreeding eggBreeding = new TEggBreeding();
        eggBreeding.setUserId(requestDto.getUserId());
        eggBreeding.setNum(requestDto.getNum());
        eggBreeding.setNumHarvest(0);
        eggBreeding.setDay(0);
        eggBreeding.setAccNoHot(0);
        eggBreeding.setStatus(1);
        eggBreeding.setAddTime(new Date());
        eggBreeding.setUpdateTime(new Date());
        updateCount = tEggBreedingMapper.insertSelective(eggBreeding);
        if(updateCount !=1){
            throw new BizException(ErrorCode.ADD_ERR);
        }
        updateCount = tEggWarehouseMapper.updateEggWareHouseBreedingEgg(requestDto);
        if(updateCount !=1){
            throw new BizException(ErrorCode.UPDATE_ERR);
        }
        return true;
    }
}
