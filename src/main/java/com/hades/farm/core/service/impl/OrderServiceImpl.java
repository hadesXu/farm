package com.hades.farm.core.service.impl;

import com.hades.farm.core.data.dto.requestDto.BuyGoodsRequestDto;
import com.hades.farm.core.data.dto.requestDto.UpdateAccountTicketRequestDto;
import com.hades.farm.core.data.dto.requestDto.UpdatePlatFormWarehouseRequestDto;
import com.hades.farm.core.data.entity.TAccountTicket;
import com.hades.farm.core.data.entity.TAccountTicketFlow;
import com.hades.farm.core.data.entity.TDuckWarehouse;
import com.hades.farm.core.data.entity.TNotice;
import com.hades.farm.core.data.mapper.*;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.core.service.OrderService;
import com.hades.farm.enums.AcctOpreType;
import com.hades.farm.enums.GoodsType;
import com.hades.farm.enums.NoticeType;
import com.hades.farm.result.ErrorCode;
import com.hades.farm.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhengzl on 2018/3/10.
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private TPlatformWarehouseMapper tPlatformWarehouseMapper;
    @Autowired
    private TEggWarehouseMapper tEggWarehouseMapper;
    @Autowired
    private TDuckWarehouseMapper tDuckWarehouseMapper;
    @Autowired
    private TAccountTicketMapper tAccountTicketMapper;
    @Autowired
    private TAccountTicketFlowMapper tAccountTicketFlowMapper;
    @Autowired
    private TNoticeMapper tNoticeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean buyDuckFromPlatform(BuyGoodsRequestDto requestDto) throws BizException{
        int updateCount = 0;
        //更新平台仓库表
        UpdatePlatFormWarehouseRequestDto updatePlatFormWarehouseRequestDto = new UpdatePlatFormWarehouseRequestDto();
        updatePlatFormWarehouseRequestDto.setUpdateDuckNum(requestDto.getNum());
        updateCount = tPlatformWarehouseMapper.updatePlatFormWarehouse(updatePlatFormWarehouseRequestDto);
        if(updateCount!=1){
            throw new BizException(ErrorCode.PLATFORM_DUCK_NO_ENOUGH);
        }
        //鸭仓库表，如果没有仓库则新增一条仓库记录
        TDuckWarehouse duckWarehouse = tDuckWarehouseMapper.selectByUserId(requestDto.getUserId());
        if(duckWarehouse == null){
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
            updateCount = tDuckWarehouseMapper.insertSelective(duckWarehouse);
            if(updateCount !=1){
                throw new BizException(ErrorCode.ADD_ERR);
            }
        }
        updateCount = tDuckWarehouseMapper.updateDuckWareHouseBuyDuck(requestDto);
        if(updateCount!=1){
            throw new BizException(ErrorCode.UPDATE_ERR);
        }
        //更新账户表
        TAccountTicket accountTicketBefore = tAccountTicketMapper.queryAccountByUserId(requestDto.getUserId());
        UpdateAccountTicketRequestDto updateAccountTicketRequestDto = new UpdateAccountTicketRequestDto();
        updateAccountTicketRequestDto.setUserId(requestDto.getUserId());
        updateAccountTicketRequestDto.setAcctOpreType(AcctOpreType.BUY_DUCK.getType());
        BigDecimal balance = Constant.DUCK_PRICE.multiply(new BigDecimal(requestDto.getNum()));
        updateAccountTicketRequestDto.setBalance(balance);
        updateCount = tAccountTicketMapper.updateAccountTicket(updateAccountTicketRequestDto);
        if(updateCount!=1){
            throw new BizException(ErrorCode.TICKET_NO_ENOUGH);
        }
        //新增账户流水表记录
        TAccountTicketFlow accountTicketFlow = new  TAccountTicketFlow();
        accountTicketFlow.setUserId(requestDto.getUserId());
        accountTicketFlow.setType(AcctOpreType.BUY_DUCK.getType());
        accountTicketFlow.setAmount(balance);
        accountTicketFlow.setAmountBefore(accountTicketBefore.getBalance());
        accountTicketFlow.setAmountAfter(accountTicketBefore.getBalance().subtract(balance));
        accountTicketFlow.setRemarks("买鸭花费菜票：" + balance);
        accountTicketFlow.setAddTime(new Date());
        updateCount = tAccountTicketFlowMapper.insertSelective(accountTicketFlow);
        if(updateCount!=1){
            throw new BizException(ErrorCode.ADD_ERR);
        }
        //添加日志记录t_notice
        TNotice tNotice = new TNotice();
        tNotice.setUserId(requestDto.getUserId());
        tNotice.setType(NoticeType.BUY_DUCK.getType());
        tNotice.setRemarks(NoticeType.BUY_DUCK.getRemarks().replace("num", requestDto.getNum() + ""));
        tNotice.setAddTime(new Date());
        tNoticeMapper.insertSelective(tNotice);
        return true;
    }

    @Override
    public boolean buyFeed(BuyGoodsRequestDto requestDto) throws BizException{
        int updateCount = 0;
        TDuckWarehouse duckWarehouse = tDuckWarehouseMapper.selectByUserId(requestDto.getUserId());
        if(duckWarehouse == null){
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
            updateCount = tDuckWarehouseMapper.insertSelective(duckWarehouse);
            if(updateCount !=1){
                throw new BizException(ErrorCode.ADD_ERR);
            }
        }
        //更新鸭仓库表
        updateCount = tDuckWarehouseMapper.updateDuckWareHouseBuyFeed(requestDto);
        if(updateCount!=1){
            throw new BizException(ErrorCode.UPDATE_ERR);
        }
        //更新账户表
        TAccountTicket accountTicketBefore = tAccountTicketMapper.queryAccountByUserId(requestDto.getUserId());
        UpdateAccountTicketRequestDto updateAccountTicketRequestDto = new UpdateAccountTicketRequestDto();
        updateAccountTicketRequestDto.setUserId(requestDto.getUserId());
        updateAccountTicketRequestDto.setAcctOpreType(AcctOpreType.BUY_FEED.getType());
        BigDecimal balance = Constant.FEED_PRICE.multiply(requestDto.getFeedNum());
        updateAccountTicketRequestDto.setBalance(balance);
        updateCount = tAccountTicketMapper.updateAccountTicket(updateAccountTicketRequestDto);
        if(updateCount!=1){
            throw new BizException(ErrorCode.TICKET_NO_ENOUGH);
        }
        //新增账户流水表记录
        TAccountTicketFlow accountTicketFlow = new  TAccountTicketFlow();
        accountTicketFlow.setUserId(requestDto.getUserId());
        accountTicketFlow.setType(AcctOpreType.BUY_FEED.getType());
        accountTicketFlow.setAmount(balance);
        accountTicketFlow.setAmountBefore(accountTicketBefore.getBalance());
        accountTicketFlow.setAmountAfter(accountTicketBefore.getBalance().subtract(balance));
        accountTicketFlow.setRemarks("买饲料花费菜票：" + balance);
        accountTicketFlow.setAddTime(new Date());
        updateCount = tAccountTicketFlowMapper.insertSelective(accountTicketFlow);
        if(updateCount!=1){
            throw new BizException(ErrorCode.ADD_ERR);
        }
        //添加日志记录t_notice
        TNotice tNotice = new TNotice();
        tNotice.setUserId(requestDto.getUserId());
        tNotice.setType(NoticeType.BUY_FEED.getType());
        tNotice.setRemarks(NoticeType.BUY_FEED.getRemarks().replace("num", requestDto.getFeedNum() + ""));
        tNotice.setAddTime(new Date());
        tNoticeMapper.insertSelective(tNotice);
        return true;
    }
    @Override
    public boolean buyEggFromPlatform(BuyGoodsRequestDto requestDto) throws BizException{
        //
        return true;
    }

    @Override
    public boolean buyEggFromOrder(BuyGoodsRequestDto requestDto) throws BizException{
        //
        return true;
    }

    @Override
    public boolean buyDuckFromOrder(BuyGoodsRequestDto requestDto) throws BizException{
        //
        return true;
    }

}
