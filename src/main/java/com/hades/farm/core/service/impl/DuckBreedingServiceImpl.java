package com.hades.farm.core.service.impl;

import com.hades.farm.core.data.dto.requestDto.BreedingRequestDto;
import com.hades.farm.core.data.dto.requestDto.QueryDuckBreedingRequestDto;
import com.hades.farm.core.data.dto.requestDto.UpdateDuckWareHouseFeedingRequestDto;
import com.hades.farm.core.data.dto.requestDto.UpdateOfFeedingRequestDto;
import com.hades.farm.core.data.entity.TDuckBreeding;
import com.hades.farm.core.data.entity.TDuckWarehouse;
import com.hades.farm.core.data.entity.TNotice;
import com.hades.farm.core.data.mapper.TDuckBreedingMapper;
import com.hades.farm.core.data.mapper.TDuckWarehouseMapper;
import com.hades.farm.core.data.mapper.TNoticeMapper;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.core.service.DuckBreedingService;
import com.hades.farm.enums.NoticeType;
import com.hades.farm.result.ErrorCode;
import com.hades.farm.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
        duckBreeding.setIsFeed(2);
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

    /**
     * 给鸭喂饲料
     * @param userId
     * @return
     * @throws BizException
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean feeding(long userId) throws BizException{
        TDuckWarehouse duckWarehouse = tDuckWarehouseMapper.selectByUserId(userId);
        if(duckWarehouse == null || duckWarehouse.getDuckDoing() == 0){
            throw new BizException(ErrorCode.NO_DUCK_DOING);
        }
        int needFoodAmount = Constant.DUCK_SINGLE_FEED_AMOUNT*duckWarehouse.getDuckDoing();
        if(duckWarehouse.getFood()<needFoodAmount){
            throw new BizException(ErrorCode.FOOD_NOT_ENOUGH);
        }
        QueryDuckBreedingRequestDto requestDto = new QueryDuckBreedingRequestDto();
        requestDto.setUserId(userId);
        requestDto.setStatus(1);
        List<TDuckBreeding> duckBreedings = tDuckBreedingMapper.queryDuckBreeding(requestDto);
        int breedingDucks = 0;
        Long[] duckBreedingIds = new Long[duckBreedings.size()];
        for(int i=0;i<duckBreedings.size();i++){
            TDuckBreeding duckBreeding = duckBreedings.get(i);
            breedingDucks = breedingDucks+duckBreeding.getNum();
            duckBreedingIds[i] = duckBreeding.getId();
        }
        if(breedingDucks != duckWarehouse.getDuckDoing()){
            throw new BizException(ErrorCode.DUCK_DOING_NUM_NOT_MATCH);
        }
        //更新养殖表
        UpdateOfFeedingRequestDto updateOfFeedingRequestDto = new UpdateOfFeedingRequestDto();
        updateOfFeedingRequestDto.setUserId(userId);
        updateOfFeedingRequestDto.setIds(duckBreedingIds);
        int updateCount = tDuckBreedingMapper.updateOfFeeding(updateOfFeedingRequestDto);
        if(updateCount != duckBreedings.size()){
            throw new BizException(ErrorCode.HAS_FEED);
        }
        //更新仓库表
        UpdateDuckWareHouseFeedingRequestDto updateDuckWareHouseFeedingRequestDto = new UpdateDuckWareHouseFeedingRequestDto();
        updateDuckWareHouseFeedingRequestDto.setUserId(userId);
        updateDuckWareHouseFeedingRequestDto.setFeedNum(needFoodAmount);
        updateCount = tDuckWarehouseMapper.updateDuckWareHouseOfFeeding(updateDuckWareHouseFeedingRequestDto);
        if(updateCount !=1){
            throw new BizException(ErrorCode.UPDATE_ERR);
        }
        //更新t_notice表
        TNotice tNotice = new TNotice();
        tNotice.setUserId(userId);
        tNotice.setType(NoticeType.FEED_DUCK.getType());
        tNotice.setRemarks(NoticeType.FEED_DUCK.getRemarks());
        tNotice.setAddTime(new Date());
        updateCount = tNoticeMapper.insertSelective(tNotice);
        if(updateCount !=1){
            throw new BizException(ErrorCode.UPDATE_ERR);
        }
        return true;
    }
}
