package com.hades.farm.core.service.impl;

import com.hades.farm.core.data.dto.requestDto.BuyGoodsRequestDto;
import com.hades.farm.core.data.dto.requestDto.UpdateAccountTicketRequestDto;
import com.hades.farm.core.data.dto.requestDto.UpdatePlatFormWarehouseRequestDto;
import com.hades.farm.core.data.entity.*;
import com.hades.farm.core.data.mapper.*;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.core.service.OrderService;
import com.hades.farm.core.service.WareHouseService;
import com.hades.farm.enums.AcctOpreType;
import com.hades.farm.enums.GoodsType;
import com.hades.farm.enums.NoticeType;
import com.hades.farm.result.ErrorCode;
import com.hades.farm.utils.Constant;
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

    @Autowired
    @Qualifier("duckWareHouseServiceImpl")
    private WareHouseService duckWareHouseServiceImpl;

    @Autowired
    @Qualifier("eggWareHouseServiceImpl")
    private WareHouseService eggWareHouseServiceImpl;

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
            duckWareHouseServiceImpl.addWareHouse(requestDto.getUserId());
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
    @Transactional(rollbackFor = Exception.class)
    public boolean buyFeed(BuyGoodsRequestDto requestDto) throws BizException{
        int updateCount = 0;
        TDuckWarehouse duckWarehouse = tDuckWarehouseMapper.selectByUserId(requestDto.getUserId());
        if(duckWarehouse == null){
            duckWareHouseServiceImpl.addWareHouse(requestDto.getUserId());
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
    @Transactional(rollbackFor = Exception.class)
    public boolean buyEggFromPlatform(BuyGoodsRequestDto requestDto) throws BizException{
        int updateCount = 0;
        //更新平台仓库表
        UpdatePlatFormWarehouseRequestDto updatePlatFormWarehouseRequestDto = new UpdatePlatFormWarehouseRequestDto();
        updatePlatFormWarehouseRequestDto.setUpdateEggNum(requestDto.getNum());
        updateCount = tPlatformWarehouseMapper.updatePlatFormWarehouse(updatePlatFormWarehouseRequestDto);
        if(updateCount!=1){
            throw new BizException(ErrorCode.PLATFORM_DUCK_NO_ENOUGH);
        }
        TEggWarehouse eggWarehouse = tEggWarehouseMapper.selectByUserId(requestDto.getUserId());
        if(eggWarehouse == null){
            //添加记录
            eggWareHouseServiceImpl.addWareHouse(requestDto.getUserId());
        }
        //更新个人仓库记录
        updateCount = tEggWarehouseMapper.updateEggWareHouseBuyDuck(requestDto);
        if(updateCount!=1){
            throw new BizException(ErrorCode.UPDATE_ERR);
        }
        //更新账户表
        TAccountTicket accountTicketBefore = tAccountTicketMapper.queryAccountByUserId(requestDto.getUserId());
        UpdateAccountTicketRequestDto updateAccountTicketRequestDto = new UpdateAccountTicketRequestDto();
        updateAccountTicketRequestDto.setUserId(requestDto.getUserId());
        updateAccountTicketRequestDto.setAcctOpreType(AcctOpreType.BUY_EGG.getType());
        BigDecimal balance = Constant.EGG_PRICE.multiply(new BigDecimal(requestDto.getNum()));
        updateAccountTicketRequestDto.setBalance(balance);
        updateCount = tAccountTicketMapper.updateAccountTicket(updateAccountTicketRequestDto);
        if(updateCount!=1){
            throw new BizException(ErrorCode.TICKET_NO_ENOUGH);
        }
        //新增账户流水表记录
        TAccountTicketFlow accountTicketFlow = new  TAccountTicketFlow();
        accountTicketFlow.setUserId(requestDto.getUserId());
        accountTicketFlow.setType(AcctOpreType.BUY_EGG.getType());
        accountTicketFlow.setAmount(balance);
        accountTicketFlow.setAmountBefore(accountTicketBefore.getBalance());
        accountTicketFlow.setAmountAfter(accountTicketBefore.getBalance().subtract(balance));
        accountTicketFlow.setRemarks("买鸭蛋花费菜票：" + balance);
        accountTicketFlow.setAddTime(new Date());
        updateCount = tAccountTicketFlowMapper.insertSelective(accountTicketFlow);
        if(updateCount!=1){
            throw new BizException(ErrorCode.ADD_ERR);
        }
        //添加日志记录t_notice
        TNotice tNotice = new TNotice();
        tNotice.setUserId(requestDto.getUserId());
        tNotice.setType(NoticeType.BUY_EGG.getType());
        tNotice.setRemarks(NoticeType.BUY_EGG.getRemarks().replace("num", requestDto.getNum() + ""));
        tNotice.setAddTime(new Date());
        tNoticeMapper.insertSelective(tNotice);
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
