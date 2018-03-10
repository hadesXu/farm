package com.hades.farm.core.service.impl;

import com.hades.farm.core.data.dto.requestDto.BreedingRequestDto;
import com.hades.farm.core.data.entity.TEggBreeding;
import com.hades.farm.core.data.entity.TEggWarehouse;
import com.hades.farm.core.data.entity.TNotice;
import com.hades.farm.core.data.mapper.TEggBreedingMapper;
import com.hades.farm.core.data.mapper.TEggWarehouseMapper;
import com.hades.farm.core.data.mapper.TNoticeMapper;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.core.service.EggBreedingService;
import com.hades.farm.enums.NoticeType;
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
    @Autowired
    private TNoticeMapper tNoticeMapper;

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
            throw new BizException(ErrorCode.EGG_NO_ENOUGH);
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
        //孵蛋notice记录
        TNotice tNotice = new TNotice();
        tNotice.setUserId(requestDto.getUserId());
        tNotice.setType(NoticeType.BREEDING_EGG.getType());
        tNotice.setRemarks(NoticeType.BREEDING_EGG.getRemarks().replace("num", requestDto.getNum() + ""));
        tNotice.setAddTime(new Date());
        tNoticeMapper.insertSelective(tNotice);
        return true;
    }

}
