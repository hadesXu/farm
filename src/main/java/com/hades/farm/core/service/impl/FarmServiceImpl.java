package com.hades.farm.core.service.impl;

import com.hades.farm.api.view.response.FdcInfoModel;
import com.hades.farm.api.view.response.MsgModel;
import com.hades.farm.api.view.response.YjcInfoModel;
import com.hades.farm.core.data.dto.resultDto.HarvestResultDto;
import com.hades.farm.core.data.entity.*;
import com.hades.farm.core.data.mapper.*;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.core.service.FarmService;
import com.hades.farm.enums.NoticeType;
import com.hades.farm.result.ErrorCode;
import com.hades.farm.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
    @Autowired
    private TEggBreedingMapper tEggBreedingMapper;
    @Autowired
    private TDuckBreedingMapper tDuckBreedingMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String shouhuo(long userId,String goodTypeStr) throws BizException{
        TDuckWarehouse duckWarehouse =null;
        TEggWarehouse eggWarehouse = null;
        int harvestNum = 0;
        if("1".equals(goodTypeStr)){  //蛋
            duckWarehouse = duckWareHouseService.queryDuckWareHouse(userId);
             //查询鸭养殖记录中是否有待收获的蛋
            List<HarvestResultDto> harvestList = tDuckBreedingMapper.queryCanHarvestList(userId);
             if(duckWarehouse == null || harvestList ==null || harvestList.size()==0){
                 throw new BizException(ErrorCode.NO_HARVEST_EGG.getCode(),ErrorCode.NO_HARVEST_EGG.getMessage());
             }else{
                 int harvestNumOfDone = 0;
                 for(HarvestResultDto harvestResultDto:harvestList){
                     if(harvestResultDto.getStatus() ==2){
                         harvestNumOfDone = harvestNumOfDone+harvestResultDto.getProduce();
                         harvestNumOfDone = harvestNumOfDone+harvestResultDto.getNumFreeze();
                     }
                     harvestNum = harvestNum + harvestResultDto.getProduce();
                 }
                 if(harvestNumOfDone>0){
                     tDuckWarehouseMapper.shouhuoEgg(userId,harvestNumOfDone);
                 }
                 //更新养殖表记录
                 tDuckBreedingMapper.harvestOfDoing(userId);
                 tDuckBreedingMapper.harvestOfDone(userId);
             }
         }else if("2".equals(goodTypeStr)){//鸭
            eggWarehouse = eggWareHouseService.queryEggWareHouse(userId);
            List<HarvestResultDto> harvestList = tEggBreedingMapper.queryCanHarvestList(userId);
             if(eggWarehouse == null || harvestList ==null || harvestList.size()==0){
                 throw new BizException(ErrorCode.NO_HARVEST_DUCK.getCode(),ErrorCode.NO_HARVEST_DUCK.getMessage());
             }else{
                 int harvestNumOfDone = 0;
                 for(HarvestResultDto harvestResultDto:harvestList){
                     if(harvestResultDto.getStatus() ==2){
                         harvestNumOfDone = harvestNumOfDone+harvestResultDto.getProduce();
                         harvestNumOfDone = harvestNumOfDone+harvestResultDto.getNumFreeze();
                     }
                     harvestNum = harvestNum + harvestResultDto.getProduce();
                 }
                 if(harvestNumOfDone>0){
                     tEggWarehouseMapper.shouhuoDuck(userId,harvestNumOfDone);
                 }
                 //更新养殖记录
                 tEggBreedingMapper.harvestOfDoing(userId);
                 tEggBreedingMapper.harvestOfDone(userId);
             }
         }else{
             throw new BizException(ErrorCode.GOOD_TYPE_ERROR.getCode(),ErrorCode.GOOD_TYPE_ERROR.getMessage());
         }
        //t_notice
        TNotice tNotice = new TNotice();
        tNotice.setUserId(userId);
        String msgStr = "";
        if("1".equals(goodTypeStr)){
            tNotice.setType(NoticeType.HARVEST_EGG.getType());
            tNotice.setRemarks(NoticeType.HARVEST_EGG.getRemarks().replace("num", harvestNum + ""));
            msgStr = NoticeType.HARVEST_EGG.getRemarks().replace("num", harvestNum + "");
        }else{
            tNotice.setType(NoticeType.HARVEST_DUCK.getType());
            tNotice.setRemarks(NoticeType.HARVEST_DUCK.getRemarks().replace("num", harvestNum + ""));
            msgStr = NoticeType.HARVEST_DUCK.getRemarks().replace("num", harvestNum + "");
        }
        tNotice.setAddTime(new Date());
        tNoticeMapper.insertSelective(tNotice);
        return  msgStr;
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
        // yjcInfoModel.setHavestNum(duckWarehouse.getEggHarvest());
        List<HarvestResultDto> harvestList = tDuckBreedingMapper.queryCanHarvestList(userId);
        if(harvestList!=null && harvestList.size()>0){
            yjcInfoModel.setHavestNum(1);
        }else{
            yjcInfoModel.setHavestNum(0);
        }
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
        fdcInfoModel.setIfHot(eggWarehouse.getIfHot() + "");
        // fdcInfoModel.setHavestNum(eggWarehouse.getDuckHarvest());
        List<HarvestResultDto> harvestList = tEggBreedingMapper.queryCanHarvestList(userId);
        if(harvestList!=null && harvestList.size()>0){
            fdcInfoModel.setHavestNum(1);
        }else{
            fdcInfoModel.setHavestNum(0);
        }
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
        //设置养殖记录  queryFdcInfo
        List<TEggBreeding> eggBreedingList = tEggBreedingMapper.queryBreedingList(userId);
        fdcInfoModel.setEggBreedingList(eggBreedingList);
        fdcInfoModel.setsEgg(user.getsEgg());
        return fdcInfoModel;
    }
}
