package com.hades.farm.core.service.impl;

import com.hades.farm.core.data.dto.requestDto.BuyGoodsRequestDto;
import com.hades.farm.core.data.dto.requestDto.PublishOrderRequestDto;
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
    private UserMapper userMapper;
    @Autowired
    private TOrdersMapper tOrdersMapper;

    @Autowired
    @Qualifier("duckWareHouseServiceImpl")
    private WareHouseService duckWareHouseServiceImpl;

    @Autowired
    @Qualifier("eggWareHouseServiceImpl")
    private WareHouseService eggWareHouseServiceImpl;

    /**
     * 从平台购买种鸭
     * @param requestDto
     * @return
     * @throws BizException
     */
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
        updateCount = tNoticeMapper.insertSelective(tNotice);
        if(updateCount !=1){
            throw new BizException(ErrorCode.ADD_ERR);
        }
        return true;
    }

    /**
     * 从平台购买鸭蛋
     * @param requestDto
     * @return
     * @throws BizException
     */
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
        updateCount = tNoticeMapper.insertSelective(tNotice);
        if(updateCount !=1){
            throw new BizException(ErrorCode.ADD_ERR);
        }
        return true;
    }

    /**
     * 购买看门狗
     * @param requestDto
     * @return
     * @throws BizException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean buyDoorDog(BuyGoodsRequestDto requestDto) throws BizException{
        int updateCount = 0;
        //更新用户表看门狗的到期时间
        updateCount = userMapper.updateDogEndDay(requestDto);
        if(updateCount!=1){
            throw new BizException(ErrorCode.UPDATE_ERR);
        }
        //更新账户表
        TAccountTicket accountTicketBefore = tAccountTicketMapper.queryAccountByUserId(requestDto.getUserId());
        UpdateAccountTicketRequestDto updateAccountTicketRequestDto = new UpdateAccountTicketRequestDto();
        updateAccountTicketRequestDto.setUserId(requestDto.getUserId());
        updateAccountTicketRequestDto.setAcctOpreType(AcctOpreType.BUY_DOG.getType());
        BigDecimal balance = Constant.DOG_PRICE.multiply(new BigDecimal(requestDto.getNum())).divide(new BigDecimal(30));
        updateAccountTicketRequestDto.setBalance(balance);
        updateCount = tAccountTicketMapper.updateAccountTicket(updateAccountTicketRequestDto);
        if(updateCount!=1){
            throw new BizException(ErrorCode.TICKET_NO_ENOUGH);
        }
        //新增账户流水表记录
        TAccountTicketFlow accountTicketFlow = new  TAccountTicketFlow();
        accountTicketFlow.setUserId(requestDto.getUserId());
        accountTicketFlow.setType(AcctOpreType.BUY_DOG.getType());
        accountTicketFlow.setAmount(balance);
        accountTicketFlow.setAmountBefore(accountTicketBefore.getBalance());
        accountTicketFlow.setAmountAfter(accountTicketBefore.getBalance().subtract(balance));
        accountTicketFlow.setRemarks("买看门狗花费菜票：" + balance);
        accountTicketFlow.setAddTime(new Date());
        updateCount = tAccountTicketFlowMapper.insertSelective(accountTicketFlow);
        if(updateCount!=1){
            throw new BizException(ErrorCode.ADD_ERR);
        }
        //添加日志记录t_notice
        TNotice tNotice = new TNotice();
        tNotice.setUserId(requestDto.getUserId());
        tNotice.setType(NoticeType.BUY_DOG.getType());
        tNotice.setRemarks(NoticeType.BUY_DOG.getRemarks().replace("num", requestDto.getNum() + ""));
        tNotice.setAddTime(new Date());
        updateCount = tNoticeMapper.insertSelective(tNotice);
        if(updateCount !=1){
            throw new BizException(ErrorCode.ADD_ERR);
        }
        return true;
    }

    /**
     * 购买机器人
     * @param requestDto
     * @return
     * @throws BizException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean buyRobot(BuyGoodsRequestDto requestDto) throws BizException{
        int updateCount = 0;
        //更新用户表机器人的到期时间
        updateCount = userMapper.updateRobotEndDay(requestDto);
        if(updateCount!=1){
            throw new BizException(ErrorCode.UPDATE_ERR);
        }
        //更新账户表
        TAccountTicket accountTicketBefore = tAccountTicketMapper.queryAccountByUserId(requestDto.getUserId());
        UpdateAccountTicketRequestDto updateAccountTicketRequestDto = new UpdateAccountTicketRequestDto();
        updateAccountTicketRequestDto.setUserId(requestDto.getUserId());
        updateAccountTicketRequestDto.setAcctOpreType(AcctOpreType.BUY_ROBOT.getType());
        BigDecimal balance = Constant.ROBOT_PRICE.multiply(new BigDecimal(requestDto.getNum())).divide(new BigDecimal(30));
        updateAccountTicketRequestDto.setBalance(balance);
        updateCount = tAccountTicketMapper.updateAccountTicket(updateAccountTicketRequestDto);
        if(updateCount!=1){
            throw new BizException(ErrorCode.TICKET_NO_ENOUGH);
        }
        //新增账户流水表记录
        TAccountTicketFlow accountTicketFlow = new  TAccountTicketFlow();
        accountTicketFlow.setUserId(requestDto.getUserId());
        accountTicketFlow.setType(AcctOpreType.BUY_ROBOT.getType());
        accountTicketFlow.setAmount(balance);
        accountTicketFlow.setAmountBefore(accountTicketBefore.getBalance());
        accountTicketFlow.setAmountAfter(accountTicketBefore.getBalance().subtract(balance));
        accountTicketFlow.setRemarks("买机器人花费菜票：" + balance);
        accountTicketFlow.setAddTime(new Date());
        updateCount = tAccountTicketFlowMapper.insertSelective(accountTicketFlow);
        if(updateCount!=1){
            throw new BizException(ErrorCode.ADD_ERR);
        }
        //添加日志记录t_notice
        TNotice tNotice = new TNotice();
        tNotice.setUserId(requestDto.getUserId());
        tNotice.setType(NoticeType.BUY_ROBOT.getType());
        tNotice.setRemarks(NoticeType.BUY_ROBOT.getRemarks().replace("num", requestDto.getNum() + ""));
        tNotice.setAddTime(new Date());
        updateCount = tNoticeMapper.insertSelective(tNotice);
        if(updateCount !=1){
            throw new BizException(ErrorCode.ADD_ERR);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean buyEggFromOrder(BuyGoodsRequestDto requestDto) throws BizException{
        //
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean buyDuckFromOrder(BuyGoodsRequestDto requestDto) throws BizException{
        //
        return true;
    }

    /**
     * 发布订单
     * @param requestDto
     * @return
     * @throws BizException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean publishOrders(PublishOrderRequestDto requestDto) throws BizException{
        int updateCount = 0;
        //更新个人仓库表
        if(GoodsType.EGG.getType() == requestDto.getType()){
            updateCount = tDuckWarehouseMapper.updateDuckWareHouseSellEgg(requestDto);
            if(updateCount !=1){
                throw new BizException(ErrorCode.EGG_NO_ENOUGH);
            }
        }else if(GoodsType.DUCK.getType() == requestDto.getType()){
            updateCount = tEggWarehouseMapper.updateEggWareHouseSellDuck(requestDto);
            if(updateCount !=1){
                throw new BizException(ErrorCode.DUCK_NO_ENOUGH);
            }
        }else{
            throw new BizException(ErrorCode.GOOD_TYPE_ERROR);
        }
        //插入订单表
        TOrders orders = new TOrders();
        orders.setUserId(requestDto.getUserId());
        orders.setType(requestDto.getType());
        orders.setNum(requestDto.getNum());
        orders.setStatus(1);
        orders.setAddTime(new Date());
        orders.setUpdateTime(new Date());
        orders.setIfCashback(2);
        updateCount = tOrdersMapper.insertSelective(orders);
        if(updateCount !=1){
            throw new BizException(ErrorCode.ADD_ERR);
        }
        //插入t_notice
        TNotice tNotice = new TNotice();
        tNotice.setUserId(requestDto.getUserId());
        if(GoodsType.EGG.getType() == requestDto.getType()){
            tNotice.setType(NoticeType.SELL_EGG.getType());
            tNotice.setRemarks(NoticeType.SELL_EGG.getRemarks().replace("num", requestDto.getNum() + ""));
        }else if(GoodsType.DUCK.getType() == requestDto.getType()){
            tNotice.setType(NoticeType.SELL_DUCK.getType());
            tNotice.setRemarks(NoticeType.SELL_DUCK.getRemarks().replace("num", requestDto.getNum() + ""));
        }
        tNotice.setAddTime(new Date());
        updateCount = tNoticeMapper.insertSelective(tNotice);
        if(updateCount !=1){
            throw new BizException(ErrorCode.ADD_ERR);
        }
        return true;
    }

    /**
     * 购买饲料
     * @param requestDto
     * @return
     * @throws BizException
     */
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
        updateCount = tNoticeMapper.insertSelective(tNotice);
        if(updateCount !=1){
            throw new BizException(ErrorCode.ADD_ERR);
        }
        return true;
    }



}
