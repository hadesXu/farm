package com.hades.farm.core.service.impl;

import com.hades.farm.api.view.response.StealModel;
import com.hades.farm.core.data.entity.TEggWarehouse;
import com.hades.farm.core.data.entity.TNotice;
import com.hades.farm.core.data.mapper.TEggBreedingMapper;
import com.hades.farm.core.data.mapper.TEggWarehouseMapper;
import com.hades.farm.core.data.mapper.TNoticeMapper;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.enums.NoticeType;
import com.hades.farm.result.ErrorCode;
import com.hades.farm.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengzl on 2018/3/10.
 */
@Service
public class EggWareHouseServiceImpl{
    @Autowired
    private TEggWarehouseMapper tEggWarehouseMapper;
    @Autowired
    private TNoticeMapper tNoticeMapper;

    @Autowired
    private TEggBreedingMapper tEggBreedingMapper;

    public void addWareHouse(long userId) throws BizException{
        TEggWarehouse eggWarehouse = new TEggWarehouse();
        eggWarehouse.setUserId(userId);
        eggWarehouse.setEgg(0);
        eggWarehouse.setEggDoing(0);
        eggWarehouse.setDuck(0);
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
       return tEggWarehouseMapper.queryCanStealList(userId, offSet, pageSize);
    }

    public boolean checkIsSteal(long userId, int type) {
        String s = DateUtils.dateToYYMMDDStr(new Date());
        String start = s+" 00:00:00";
        String end = s+" 23:59:59";
        Map map = new HashMap();
        map.put("userId",userId);
        map.put("type",type);
        map.put("start_time",DateUtils.strToDate(start));
        map.put("end_time",DateUtils.strToDate(end));
        int count = tNoticeMapper.queryByType(map);
        if(count > 0) {
            return false;
        }else{
            return true;
        }

    }

    /**
     * 偷鸭逻辑
     * @param userId
     * @param targetUserId
     */
    @Transactional(rollbackFor = Exception.class)
    public void stealDuck(long userId,long targetUserId) throws BizException{
        TEggWarehouse targetEggWarehouse = tEggWarehouseMapper.selectByUserId(targetUserId);
        int stealNum = tEggBreedingMapper.queryCanStealNum(targetUserId);
        if(targetEggWarehouse.getIfSteal() ==2 || stealNum==0){
            throw new BizException(ErrorCode.CANNT_STEAL.getCode(),
                    ErrorCode.CANNT_STEAL.getMessage());
        }
        int updateCount = tEggWarehouseMapper.updateOfStealByOther(targetUserId, stealNum);
        if(updateCount<1){
            throw new BizException(ErrorCode.CANNT_STEAL.getCode(),
                    ErrorCode.CANNT_STEAL.getMessage());
        }
        //更新目标用户被偷得鸭
        updateCount = tEggBreedingMapper.updateOfStealByOther(targetUserId);
        //更新本用户偷到的鸭
        updateCount =tEggWarehouseMapper.updateOfStealOther(userId, stealNum);
        TNotice thisNotice = new TNotice();
        thisNotice.setUserId(userId);
        thisNotice.setType(NoticeType.STEAL_DUCK_OTHER_WAREHOUSE.getType());
        thisNotice.setRemarks(NoticeType.STEAL_DUCK_OTHER_WAREHOUSE.getRemarks().replaceAll("num", stealNum + ""));
        thisNotice.setAddTime(new Date());
        tNoticeMapper.insertSelective(thisNotice);

        TNotice targetNotice = new TNotice();
        targetNotice.setUserId(targetUserId);
        targetNotice.setType(NoticeType.STEAL_DUCK_BYOTHER_WAREHOUSE.getType());
        targetNotice.setRemarks(NoticeType.STEAL_DUCK_BYOTHER_WAREHOUSE.getRemarks());
        targetNotice.setAddTime(new Date());
        tNoticeMapper.insertSelective(targetNotice);
    }
}
