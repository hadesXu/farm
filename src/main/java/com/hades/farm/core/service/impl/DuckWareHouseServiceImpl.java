package com.hades.farm.core.service.impl;

import com.hades.farm.api.view.response.StealModel;
import com.hades.farm.core.data.entity.TDuckBreeding;
import com.hades.farm.core.data.entity.TDuckWarehouse;
import com.hades.farm.core.data.entity.TNotice;
import com.hades.farm.core.data.mapper.TDuckBreedingMapper;
import com.hades.farm.core.data.mapper.TDuckWarehouseMapper;
import com.hades.farm.core.data.mapper.TEggBreedingMapper;
import com.hades.farm.core.data.mapper.TNoticeMapper;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.enums.NoticeType;
import com.hades.farm.result.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by zhengzl on 2018/3/10.
 */
@Service
public class DuckWareHouseServiceImpl {
    @Autowired
    private TDuckWarehouseMapper tDuckWarehouseMapper;

    @Autowired
    private TNoticeMapper tNoticeMapper;

    @Autowired
    private TDuckBreedingMapper tDuckBreedingMapper;



    public void addWareHouse(long userId) throws BizException{
        //添加仓库记录
        TDuckWarehouse duckWarehouse = new TDuckWarehouse();
        duckWarehouse.setUserId(userId);
        duckWarehouse.setDuck(0);
        duckWarehouse.setDuckDoing(0);
        duckWarehouse.setEgg(0);
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

    public List<StealModel> queryCanStealList(long userId,int offSet,int pageSize){
        return tDuckWarehouseMapper.queryCanStealList(userId, offSet, pageSize);
    }

    /**
     * 偷蛋逻辑
     * @param userId
     * @param targetUserId
     */
    @Transactional(rollbackFor = Exception.class)
    public void stealEgg(long userId,long targetUserId) throws BizException{
       //1.校验目标用户是否可偷，查询出可偷的数量
        TDuckWarehouse targetDuckWarehouse = tDuckWarehouseMapper.selectByUserId(targetUserId);
        int stealNum = tDuckBreedingMapper.queryCanStealNum(targetUserId);
        if(targetDuckWarehouse.getIfSteal() == 2 || stealNum == 0){
            throw new BizException(ErrorCode.CANNT_STEAL.getCode(),
                    ErrorCode.CANNT_STEAL.getMessage());
        }
        //2.更新目标用户的可偷标识
        int updateCount = tDuckWarehouseMapper.updateOfStealByOther(targetUserId, stealNum);
        if(updateCount<1){
            throw new BizException(ErrorCode.CANNT_STEAL.getCode(),
                    ErrorCode.CANNT_STEAL.getMessage());
        }
        //.更新目标用户养殖记录中produce数量
        updateCount = tDuckBreedingMapper.updateOfStealByOther(targetUserId);
        //3.更新本用户的egg数量
        updateCount =tDuckWarehouseMapper.updateOfStealOther(userId, stealNum);
        //4.添加提示记录
        TNotice thisNotice = new TNotice();
        thisNotice.setUserId(userId);
        thisNotice.setType(NoticeType.STEAL_EGG_OTHER_WAREHOUSE.getType());
        thisNotice.setRemarks(NoticeType.STEAL_EGG_OTHER_WAREHOUSE.getRemarks().replaceAll("num", stealNum + ""));
        thisNotice.setAddTime(new Date());
        tNoticeMapper.insertSelective(thisNotice);

        TNotice targetNotice = new TNotice();
        targetNotice.setUserId(targetUserId);
        targetNotice.setType(NoticeType.STEAL_EGG_BYOTHER_WAREHOUSE.getType());
        targetNotice.setRemarks(NoticeType.STEAL_EGG_BYOTHER_WAREHOUSE.getRemarks());
        targetNotice.setAddTime(new Date());
        tNoticeMapper.insertSelective(targetNotice);
    }
}
