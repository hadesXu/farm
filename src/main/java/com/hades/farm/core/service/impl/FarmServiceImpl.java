package com.hades.farm.core.service.impl;

import com.hades.farm.api.view.response.FdcInfoModel;
import com.hades.farm.api.view.response.MsgModel;
import com.hades.farm.api.view.response.YjcInfoModel;
import com.hades.farm.core.data.entity.TDuckWarehouse;
import com.hades.farm.core.data.entity.TEggWarehouse;
import com.hades.farm.core.data.entity.TNotice;
import com.hades.farm.core.data.entity.User;
import com.hades.farm.core.data.mapper.TDuckWarehouseMapper;
import com.hades.farm.core.data.mapper.TEggWarehouseMapper;
import com.hades.farm.core.data.mapper.TNoticeMapper;
import com.hades.farm.core.data.mapper.UserMapper;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.core.service.FarmService;
import com.hades.farm.enums.NoticeType;
import com.hades.farm.result.ErrorCode;
import com.hades.farm.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by zhengzl on 2018/3/24.
 */
@Service
public class FarmServiceImpl implements FarmService {

    @Autowired
    private DuckWareHouseServiceImpl duckWareHouseService;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EggWareHouseServiceImpl eggWareHouseService;
    @Autowired
    private TEggWarehouseMapper tEggWarehouseMapper;
    @Autowired
    private TDuckWarehouseMapper tDuckWarehouseMapper;
    @Autowired
    private TNoticeMapper tNoticeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void shouhuo(long userId,String goodTypeStr) throws BizException{
        TDuckWarehouse duckWarehouse =null;
        TEggWarehouse eggWarehouse = null;
        if("1".equals(goodTypeStr)){  //蛋
            duckWarehouse = duckWareHouseService.queryDuckWareHouse(userId);
             if(duckWarehouse == null || duckWarehouse.getEggHarvest()<=0){
                 throw new BizException(ErrorCode.NO_HARVEST_EGG.getCode(),ErrorCode.NO_HARVEST_EGG.getMessage());
             }else{
                 tDuckWarehouseMapper.shouhuoEgg(userId);
             }
         }else if("2".equals(goodTypeStr)){//鸭
            eggWarehouse = eggWareHouseService.queryEggWareHouse(userId);
             if(eggWarehouse == null || eggWarehouse.getDuckHarvest()<=0){
                 throw new BizException(ErrorCode.NO_HARVEST_DUCK.getCode(),ErrorCode.NO_HARVEST_DUCK.getMessage());
             }else{
                 tEggWarehouseMapper.shouhuoDuck(userId);
             }
         }else{
             throw new BizException(ErrorCode.GOOD_TYPE_ERROR.getCode(),ErrorCode.GOOD_TYPE_ERROR.getMessage());
         }
        //t_notice
        TNotice tNotice = new TNotice();
        tNotice.setUserId(userId);
        if("1".equals(goodTypeStr)){
            tNotice.setType(NoticeType.HARVEST_EGG.getType());
            tNotice.setRemarks(NoticeType.HARVEST_EGG.getRemarks().replace("num", duckWarehouse.getEggHarvest() + ""));
        }else{
            tNotice.setType(NoticeType.HARVEST_DUCK.getType());
            tNotice.setRemarks(NoticeType.HARVEST_DUCK.getRemarks().replace("num", eggWarehouse.getDuckHarvest() + ""));
        }
        tNotice.setAddTime(new Date());
        tNoticeMapper.insertSelective(tNotice);
    }

    @Override
    public YjcInfoModel queryYjcInfo(long userId) throws BizException {
        YjcInfoModel yjcInfoModel = new YjcInfoModel();
        TDuckWarehouse duckWarehouse = duckWareHouseService.queryDuckWareHouse(userId);
        if(duckWarehouse == null){
            duckWareHouseService.addWareHouse(userId);
        }
        duckWarehouse = duckWareHouseService.queryDuckWareHouse(userId);
        yjcInfoModel.setDuck(duckWarehouse.getDuck());
        yjcInfoModel.setDuckDoing(duckWarehouse.getDuckDoing());
        yjcInfoModel.setFood(duckWarehouse.getFood());
        yjcInfoModel.setIfSteal(duckWarehouse.getIfSteal());
        yjcInfoModel.setHavestNum(duckWarehouse.getEggHarvest());
        User user = userMapper.getUserById(userId);
        if(user.getDogEndDay() == null){
            yjcInfoModel.setHasDog(2);
        }else{
            if(DateUtils.isDateBefore2(DateUtils.dateToString(user.getDogEndDay()),DateUtils.dateToString(new Date()))){
                yjcInfoModel.setHasDog(1);
            }else {
                yjcInfoModel.setHasDog(2);
            }
        }
        yjcInfoModel.setsDuck(user.getsDuck());
        return yjcInfoModel;
    }

    @Override
    public FdcInfoModel queryFdcInfo(long userId) throws BizException{
        FdcInfoModel fdcInfoModel = new FdcInfoModel();
        TEggWarehouse eggWarehouse = eggWareHouseService.queryEggWareHouse(userId);
        if(eggWarehouse == null){
            eggWareHouseService.addWareHouse(userId);
        }
        eggWarehouse = eggWareHouseService.queryEggWareHouse(userId);
        fdcInfoModel.setEgg(eggWarehouse.getEgg());
        fdcInfoModel.setEggDoing(eggWarehouse.getEggDoing());
        fdcInfoModel.setIfSteal(eggWarehouse.getIfSteal());
        fdcInfoModel.setIfHot(eggWarehouse.getIfHot()+"");
        fdcInfoModel.setHavestNum(eggWarehouse.getDuckHarvest());
        User user = userMapper.getUserById(userId);
        if(user.getDogEndDay() == null){
            fdcInfoModel.setHasDog(2);
            fdcInfoModel.setRobotEndDay("未购买");
        }else{
            if(DateUtils.isDateBefore2(DateUtils.dateToString(user.getDogEndDay()),DateUtils.dateToString(new Date()))){
                fdcInfoModel.setHasDog(1);
                fdcInfoModel.setDogEndDay(DateUtils.dateToYYMMDDStr(user.getDogEndDay()));
            }else {
                fdcInfoModel.setHasDog(2);
                fdcInfoModel.setRobotEndDay("已到期");
            }
        }
        if(user.getRobotEndDay() == null){
            fdcInfoModel.setHasRobot(2);
            fdcInfoModel.setRobotEndDay("未购买");
        }else{
            if(DateUtils.isDateBefore2(DateUtils.dateToString(user.getRobotEndDay()),DateUtils.dateToString(new Date()))){
                fdcInfoModel.setHasRobot(1);
                fdcInfoModel.setRobotEndDay(DateUtils.dateToYYMMDDStr(user.getRobotEndDay()));
            }else {
                fdcInfoModel.setHasRobot(2);
                fdcInfoModel.setRobotEndDay("已到期");
            }
        }
        fdcInfoModel.setsEgg(user.getsEgg());
        return fdcInfoModel;
    }
}
