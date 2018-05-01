package com.hades.farm.core.service.impl;

import com.hades.farm.api.view.response.StealModel;
import com.hades.farm.core.data.dto.requestDto.BreedingRequestDto;
import com.hades.farm.core.data.entity.TEggBreeding;
import com.hades.farm.core.data.entity.TEggWarehouse;
import com.hades.farm.core.data.entity.TNotice;
import com.hades.farm.core.data.entity.User;
import com.hades.farm.core.data.mapper.TEggBreedingMapper;
import com.hades.farm.core.data.mapper.TEggWarehouseMapper;
import com.hades.farm.core.data.mapper.TNoticeMapper;
import com.hades.farm.core.data.mapper.UserMapper;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.core.service.EggBreedingService;
import com.hades.farm.enums.NoticeType;
import com.hades.farm.result.ErrorCode;
import com.hades.farm.utils.Constant;
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

    @Autowired
    private UserMapper userMapper;


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

        Map hbMap = new HashMap();
        hbMap.put("user_id",requestDto.getUserId());
        hbMap.put("startTime", DateUtils.dateToYYMMDDStr(new  Date())+" 00:00:00");
        hbMap.put("endTime", DateUtils.dateToYYMMDDStr(new  Date())+" 23:59:59");
        Map rMap = tEggBreedingMapper.queryHaveBreed(hbMap);
        if(rMap != null) {
            BigDecimal bMap = (BigDecimal) rMap.get("sumNum");
            //今日放养数量
            int iMap = bMap.intValue();
            iMap = iMap + requestDto.getNum();
            if(iMap > 100) {
                throw new BizException(ErrorCode.DREEDING_LIMIT);
            }
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

    /**
     * 给自己加温
     * @param userId
     * @return
     */
    public boolean warmSelf(long userId) throws BizException{
        //校验是否是实习期
        User user = userMapper.getUserById(userId);
        Date now = new Date();
        Date registeDate = user.getAddTime();
        long diffDays = DateUtils.diffDays(registeDate,now);
        if(diffDays< Constant.PRACTICE_DAY){
            throw new BizException(ErrorCode.PRACTICE_NOWARM_SELF);
        }
        TEggWarehouse tEggWarehouse = tEggWarehouseMapper.selectByUserId(userId);
        if(tEggWarehouse.getIfHot() == 1){
            throw new BizException(ErrorCode.HAS_WARM);
        }
        TEggWarehouse updateEggWarehouse = new TEggWarehouse();
        updateEggWarehouse.setId(tEggWarehouse.getId());
        updateEggWarehouse.setUpdateTime(now);
        updateEggWarehouse.setIfHot(1);
        int updateCount = tEggWarehouseMapper.updateByPrimaryKeySelective(updateEggWarehouse);
        if(updateCount !=1){
            throw new BizException(ErrorCode.UPDATE_ERR);
        }
        //插入t_notice
        TNotice tNotice = new TNotice();
        tNotice.setUserId(userId);
        tNotice.setType(NoticeType.EGG_HOT.getType());
        tNotice.setRemarks(NoticeType.EGG_HOT.getRemarks());
        tNotice.setAddTime(new Date());
        updateCount = tNoticeMapper.insertSelective(tNotice);
        if(updateCount !=1){
            throw new BizException(ErrorCode.UPDATE_ERR);
        }
        return true;
    }

    /**
     * 给师傅加温
     * @param userId
     * @return
     */
    public boolean warmMaster(long userId) throws BizException{
        //查询师父信息
        User user = userMapper.getUserById(userId);
        long masterUserId = user.getParentId() == null?0:user.getParentId();
        if(masterUserId <1){
            throw new BizException(ErrorCode.NO_MASTER);
        }
        User masterUser = userMapper.getUserById(masterUserId);
        if(masterUser == null){
            throw new BizException(ErrorCode.USER_NOT_EXIST);
        }
        TEggWarehouse masterEggWarehouse = tEggWarehouseMapper.selectByUserId(masterUserId);
        if(masterEggWarehouse == null){
            throw new BizException(ErrorCode.MASTER_NO_EGG);
        }
        if(masterEggWarehouse.getIfHot() == 1){
            throw new BizException(ErrorCode.HAS_WARM);
        }
        //更新师父仓库加温状态
        TEggWarehouse updateEggWarehouse = new TEggWarehouse();
        updateEggWarehouse.setId(masterEggWarehouse.getId());
        updateEggWarehouse.setUpdateTime(new Date());
        updateEggWarehouse.setIfHot(1);
        int updateCount = tEggWarehouseMapper.updateByPrimaryKeySelective(updateEggWarehouse);
        if(updateCount !=1){
            throw new BizException(ErrorCode.UPDATE_ERR);
        }
        //插入t_notice
        TNotice tNotice = new TNotice();
        tNotice.setUserId(userId);
        tNotice.setType(NoticeType.MASTER_EGG_HOT.getType());
        tNotice.setRemarks(NoticeType.MASTER_EGG_HOT.getRemarks());
        tNotice.setAddTime(new Date());
        updateCount = tNoticeMapper.insertSelective(tNotice);
        if(updateCount !=1){
            throw new BizException(ErrorCode.UPDATE_ERR);
        }
        return true;
    }


    public List<Map> queryBreeList(long userId, int offSet, int pageSize){
        return tEggBreedingMapper.queryBreeList(userId, offSet, pageSize);
    }
}
