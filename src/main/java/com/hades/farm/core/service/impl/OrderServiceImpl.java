package com.hades.farm.core.service.impl;

import com.hades.farm.core.data.dto.requestDto.*;
import com.hades.farm.core.data.entity.*;
import com.hades.farm.core.data.mapper.*;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.core.service.OrderService;
import com.hades.farm.core.service.WareHouseService;
import com.hades.farm.enums.AcctOpreType;
import com.hades.farm.enums.GoodsType;
import com.hades.farm.enums.NoticeType;
import com.hades.farm.enums.WareHouseOperType;
import com.hades.farm.result.ErrorCode;
import com.hades.farm.result.Result;
import com.hades.farm.utils.AmountUtil;
import com.hades.farm.utils.Constant;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
    private TPlatformWarehouseFlowMapper tPlatformWarehouseFlowMapper;

    @Autowired
    @Qualifier("duckWareHouseServiceImpl")
    private WareHouseService duckWareHouseServiceImpl;

    @Autowired
    @Qualifier("eggWareHouseServiceImpl")
    private WareHouseService eggWareHouseServiceImpl;

    @Autowired
    private TBackRewardMapper tBackRewardMapper;

    /**
     * 从平台购买种鸭
     *
     * @param requestDto
     * @return
     * @throws BizException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean buyDuckFromPlatform(BuyGoodsRequestDto requestDto) throws BizException {
        int updateCount = 0;
        //更新平台仓库表
        UpdatePlatFormWarehouseRequestDto updatePlatFormWarehouseRequestDto = new UpdatePlatFormWarehouseRequestDto();
        updatePlatFormWarehouseRequestDto.setUpdateDuckNum(requestDto.getNum());
        updateCount = tPlatformWarehouseMapper.updatePlatFormWarehouse(updatePlatFormWarehouseRequestDto);
        if (updateCount != 1) {
            throw new BizException(ErrorCode.PLATFORM_DUCK_NO_ENOUGH);
        }
        //添加平台仓库流水
        TPlatformWarehouse platformWarehouse = tPlatformWarehouseMapper.queryPlatformWarehouse();
        TPlatformWarehouseFlow platformWarehouseFlow = new TPlatformWarehouseFlow();
        platformWarehouseFlow.setpId(platformWarehouse.getId());
        platformWarehouseFlow.setGoods(GoodsType.DUCK.getType() + "");
        platformWarehouseFlow.setType(WareHouseOperType.SELL.getType() + "");
        platformWarehouseFlow.setNum(requestDto.getNum());
        platformWarehouseFlow.setNumBefore(platformWarehouse.getEggNum() - requestDto.getNum());
        platformWarehouseFlow.setNumAfter(platformWarehouse.getEggNum());
        platformWarehouseFlow.setRemarks(GoodsType.DUCK.getDesc());
        platformWarehouseFlow.setAddTime(new Date());
        updateCount =  tPlatformWarehouseFlowMapper.insertSelective(platformWarehouseFlow);
        if (updateCount != 1) {
            throw new BizException(ErrorCode.PLATFORM_DUCK_NO_ENOUGH);
        }
        //鸭仓库表，如果没有仓库则新增一条仓库记录
        TDuckWarehouse duckWarehouse = tDuckWarehouseMapper.selectByUserId(requestDto.getUserId());
        if (duckWarehouse == null) {
            duckWareHouseServiceImpl.addWareHouse(requestDto.getUserId());
        }
        updateCount = tDuckWarehouseMapper.updateDuckWareHouseBuyDuck(requestDto);
        if (updateCount != 1) {
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
        if (updateCount != 1) {
            throw new BizException(ErrorCode.TICKET_NO_ENOUGH);
        }
        //新增账户流水表记录
        TAccountTicketFlow accountTicketFlow = new TAccountTicketFlow();
        accountTicketFlow.setUserId(requestDto.getUserId());
        accountTicketFlow.setType(AcctOpreType.BUY_DUCK.getType());
        accountTicketFlow.setAmount(balance);
        accountTicketFlow.setAmountBefore(accountTicketBefore.getBalance());
        accountTicketFlow.setAmountAfter(accountTicketBefore.getBalance().subtract(balance));
        accountTicketFlow.setRemarks("买鸭花费菜票：" + balance);
        accountTicketFlow.setAddTime(new Date());
        updateCount = tAccountTicketFlowMapper.insertSelective(accountTicketFlow);
        if (updateCount != 1) {
            throw new BizException(ErrorCode.ADD_ERR);
        }
        //添加日志记录t_notice
        TNotice tNotice = new TNotice();
        tNotice.setUserId(requestDto.getUserId());
        tNotice.setType(NoticeType.BUY_DUCK.getType());
        tNotice.setRemarks(NoticeType.BUY_DUCK.getRemarks().replace("num", requestDto.getNum() + ""));
        tNotice.setAddTime(new Date());
        updateCount = tNoticeMapper.insertSelective(tNotice);
        if (updateCount != 1) {
            throw new BizException(ErrorCode.ADD_ERR);
        }
        return true;
    }

    /**
     * 从平台购买鸭蛋
     *
     * @param requestDto
     * @return
     * @throws BizException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean buyEggFromPlatform(BuyGoodsRequestDto requestDto) throws BizException {
        int updateCount = 0;
        //更新平台仓库表
        UpdatePlatFormWarehouseRequestDto updatePlatFormWarehouseRequestDto = new UpdatePlatFormWarehouseRequestDto();
        updatePlatFormWarehouseRequestDto.setUpdateEggNum(requestDto.getNum());
        updateCount = tPlatformWarehouseMapper.updatePlatFormWarehouse(updatePlatFormWarehouseRequestDto);
        if (updateCount != 1) {
            throw new BizException(ErrorCode.PLATFORM_DUCK_NO_ENOUGH);
        }
        //添加平台仓库流水
        TPlatformWarehouse platformWarehouse = tPlatformWarehouseMapper.queryPlatformWarehouse();
        TPlatformWarehouseFlow platformWarehouseFlow = new TPlatformWarehouseFlow();
        platformWarehouseFlow.setpId(platformWarehouse.getId());
        platformWarehouseFlow.setGoods(GoodsType.EGG.getType() + "");
        platformWarehouseFlow.setType(WareHouseOperType.SELL.getType() + "");
        platformWarehouseFlow.setNum(requestDto.getNum());
        platformWarehouseFlow.setNumBefore(platformWarehouse.getEggNum() - requestDto.getNum());
        platformWarehouseFlow.setNumAfter(platformWarehouse.getEggNum());
        platformWarehouseFlow.setRemarks(GoodsType.EGG.getDesc());
        platformWarehouseFlow.setAddTime(new Date());
        updateCount =  tPlatformWarehouseFlowMapper.insertSelective(platformWarehouseFlow);
        if (updateCount != 1) {
            throw new BizException(ErrorCode.PLATFORM_DUCK_NO_ENOUGH);
        }
        TEggWarehouse eggWarehouse = tEggWarehouseMapper.selectByUserId(requestDto.getUserId());
        if (eggWarehouse == null) {
            //添加记录
            eggWareHouseServiceImpl.addWareHouse(requestDto.getUserId());
        }
        //更新个人仓库记录
        updateCount = tEggWarehouseMapper.updateEggWareHouseBuyDuck(requestDto);
        if (updateCount != 1) {
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
        if (updateCount != 1) {
            throw new BizException(ErrorCode.TICKET_NO_ENOUGH);
        }
        //新增账户流水表记录
        TAccountTicketFlow accountTicketFlow = new TAccountTicketFlow();
        accountTicketFlow.setUserId(requestDto.getUserId());
        accountTicketFlow.setType(AcctOpreType.BUY_EGG.getType());
        accountTicketFlow.setAmount(balance);
        accountTicketFlow.setAmountBefore(accountTicketBefore.getBalance());
        accountTicketFlow.setAmountAfter(accountTicketBefore.getBalance().subtract(balance));
        accountTicketFlow.setRemarks("买鸭蛋花费菜票：" + balance);
        accountTicketFlow.setAddTime(new Date());
        updateCount = tAccountTicketFlowMapper.insertSelective(accountTicketFlow);
        if (updateCount != 1) {
            throw new BizException(ErrorCode.ADD_ERR);
        }
        //添加日志记录t_notice
        TNotice tNotice = new TNotice();
        tNotice.setUserId(requestDto.getUserId());
        tNotice.setType(NoticeType.BUY_EGG.getType());
        tNotice.setRemarks(NoticeType.BUY_EGG.getRemarks().replace("num", requestDto.getNum() + ""));
        tNotice.setAddTime(new Date());
        updateCount = tNoticeMapper.insertSelective(tNotice);
        if (updateCount != 1) {
            throw new BizException(ErrorCode.ADD_ERR);
        }
        return true;
    }

    /**
     * 购买看门狗
     *
     * @param requestDto
     * @return
     * @throws BizException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean buyDoorDog(BuyGoodsRequestDto requestDto) throws BizException {
        int updateCount = 0;
        //更新用户表看门狗的到期时间
        updateCount = userMapper.updateDogEndDay(requestDto);
        if (updateCount != 1) {
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
        if (updateCount != 1) {
            throw new BizException(ErrorCode.TICKET_NO_ENOUGH);
        }
        //新增账户流水表记录
        TAccountTicketFlow accountTicketFlow = new TAccountTicketFlow();
        accountTicketFlow.setUserId(requestDto.getUserId());
        accountTicketFlow.setType(AcctOpreType.BUY_DOG.getType());
        accountTicketFlow.setAmount(balance);
        accountTicketFlow.setAmountBefore(accountTicketBefore.getBalance());
        accountTicketFlow.setAmountAfter(accountTicketBefore.getBalance().subtract(balance));
        accountTicketFlow.setRemarks("买看门狗花费菜票：" + balance);
        accountTicketFlow.setAddTime(new Date());
        updateCount = tAccountTicketFlowMapper.insertSelective(accountTicketFlow);
        if (updateCount != 1) {
            throw new BizException(ErrorCode.ADD_ERR);
        }
        //添加日志记录t_notice
        TNotice tNotice = new TNotice();
        tNotice.setUserId(requestDto.getUserId());
        tNotice.setType(NoticeType.BUY_DOG.getType());
        tNotice.setRemarks(NoticeType.BUY_DOG.getRemarks().replace("num", requestDto.getNum() + ""));
        tNotice.setAddTime(new Date());
        updateCount = tNoticeMapper.insertSelective(tNotice);
        if (updateCount != 1) {
            throw new BizException(ErrorCode.ADD_ERR);
        }
        return true;
    }

    /**
     * 购买机器人
     *
     * @param requestDto
     * @return
     * @throws BizException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean buyRobot(BuyGoodsRequestDto requestDto) throws BizException {
        int updateCount = 0;
        //更新用户表机器人的到期时间
        updateCount = userMapper.updateRobotEndDay(requestDto);
        if (updateCount != 1) {
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
        if (updateCount != 1) {
            throw new BizException(ErrorCode.TICKET_NO_ENOUGH);
        }
        //新增账户流水表记录
        TAccountTicketFlow accountTicketFlow = new TAccountTicketFlow();
        accountTicketFlow.setUserId(requestDto.getUserId());
        accountTicketFlow.setType(AcctOpreType.BUY_ROBOT.getType());
        accountTicketFlow.setAmount(balance);
        accountTicketFlow.setAmountBefore(accountTicketBefore.getBalance());
        accountTicketFlow.setAmountAfter(accountTicketBefore.getBalance().subtract(balance));
        accountTicketFlow.setRemarks("买机器人花费菜票：" + balance);
        accountTicketFlow.setAddTime(new Date());
        updateCount = tAccountTicketFlowMapper.insertSelective(accountTicketFlow);
        if (updateCount != 1) {
            throw new BizException(ErrorCode.ADD_ERR);
        }
        //添加日志记录t_notice
        TNotice tNotice = new TNotice();
        tNotice.setUserId(requestDto.getUserId());
        tNotice.setType(NoticeType.BUY_ROBOT.getType());
        tNotice.setRemarks(NoticeType.BUY_ROBOT.getRemarks().replace("num", requestDto.getNum() + ""));
        tNotice.setAddTime(new Date());
        updateCount = tNoticeMapper.insertSelective(tNotice);
        if (updateCount != 1) {
            throw new BizException(ErrorCode.ADD_ERR);
        }
        return true;
    }

    /**
     * TODO 手续费
     * @param requestDto
     * @return
     * @throws BizException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean buyGoodsFromOrder(BuyGoodsRequestDto requestDto) throws BizException {
        int updateCount = 0;
        TOrders order = tOrdersMapper.selectByPrimaryKey(requestDto.getOrderId());
        if (order == null) {
            throw new BizException(ErrorCode.ORDER_ERROR);
        }
        if (requestDto.getUserId() == order.getUserId()) {
            throw new BizException(ErrorCode.NO_BUY_SELF_ORDER);
        }
        if (requestDto.getNum() != order.getNum()) {
            throw new BizException(ErrorCode.BUY_ALLOF_ORDER);
        }
        //更新t_orders
        updateCount = tOrdersMapper.updateOrderOfBuy(requestDto);
        if (updateCount != 1) {
            throw new BizException(ErrorCode.ORDER_STATUS_ERROR);
        }
        //更新卖家账户表、账户流水表、仓库表、t_notice
        BigDecimal amount = null;
        if (GoodsType.EGG.getType() == order.getType()) {
            amount = Constant.EGG_PRICE.multiply(new BigDecimal(requestDto.getNum()));
        } else if (GoodsType.DUCK.getType() == order.getType()) {
            amount = Constant.DUCK_PRICE.multiply(new BigDecimal(requestDto.getNum()));
        } else {
            //TODO 狗
        }
        long sellUserId = order.getUserId();
        long buyUserId = requestDto.getUserId();
        TAccountTicket sellAccountTicketBefore = tAccountTicketMapper.queryAccountByUserId(sellUserId);
        TAccountTicket buyAccountTicketBefore = tAccountTicketMapper.queryAccountByUserId(buyUserId);
        TAccountTicketFlow sellAccountTicketFlow = new TAccountTicketFlow();
        TAccountTicketFlow buyAccountTicketFlow = new TAccountTicketFlow();
        TNotice tSellNotice = new TNotice();
        TNotice tbuyNotice = new TNotice();
        UpdateAccountTicketRequestDto updateSellAccountTicketRequestDto = new UpdateAccountTicketRequestDto();
        UpdateAccountTicketRequestDto updateBuyAccountTicketRequestDto = new UpdateAccountTicketRequestDto();
        updateSellAccountTicketRequestDto.setUserId(sellUserId);
        updateSellAccountTicketRequestDto.setBalance(amount);
        updateBuyAccountTicketRequestDto.setUserId(buyUserId);
        updateBuyAccountTicketRequestDto.setBalance(amount);
        if (GoodsType.EGG.getType() == order.getType()) {
            updateSellAccountTicketRequestDto.setAcctOpreType(AcctOpreType.SELL_EGG.getType());
            updateBuyAccountTicketRequestDto.setAcctOpreType(AcctOpreType.BUY_EGG.getType());
            sellAccountTicketFlow.setType(AcctOpreType.SELL_EGG.getType());
            sellAccountTicketFlow.setRemarks("成功出售鸭蛋获得菜票：" + amount);
            buyAccountTicketFlow.setType(AcctOpreType.BUY_EGG.getType());
            buyAccountTicketFlow.setRemarks("买鸭蛋花费菜票:" + amount);
            tSellNotice.setType(NoticeType.SELL_EGG.getType());
            tSellNotice.setRemarks(NoticeType.SELL_EGG.getRemarks().replace("num", requestDto.getNum() + ""));
            tbuyNotice.setType(NoticeType.BUY_EGG.getType());
            tbuyNotice.setRemarks(NoticeType.BUY_EGG.getRemarks().replace("num", requestDto.getNum() + ""));
        } else if (GoodsType.DUCK.getType() == order.getType()) {
            updateSellAccountTicketRequestDto.setAcctOpreType(AcctOpreType.SELL_DUCK.getType());
            updateBuyAccountTicketRequestDto.setAcctOpreType(AcctOpreType.BUY_DUCK.getType());
            sellAccountTicketFlow.setType(AcctOpreType.SELL_DUCK.getType());
            sellAccountTicketFlow.setRemarks("成功出售鸭获得菜票：" + amount);
            buyAccountTicketFlow.setType(AcctOpreType.BUY_DUCK.getType());
            buyAccountTicketFlow.setRemarks("买鸭花费菜票:" + amount);
            tSellNotice.setType(NoticeType.SELL_DUCK.getType());
            tSellNotice.setRemarks(NoticeType.SELL_DUCK.getRemarks().replace("num", requestDto.getNum() + ""));
            tbuyNotice.setType(NoticeType.BUY_DUCK.getType());
            tbuyNotice.setRemarks(NoticeType.BUY_DUCK.getRemarks().replace("num", requestDto.getNum() + ""));
        } else {
            //TODO 狗
        }
        updateCount = tAccountTicketMapper.updateAccountTicket(updateSellAccountTicketRequestDto);
        if (updateCount != 1) {
            throw new BizException(ErrorCode.UPDATE_ERR);
        }
        sellAccountTicketFlow.setUserId(sellUserId);
        sellAccountTicketFlow.setAmount(amount);
        sellAccountTicketFlow.setAmountBefore(sellAccountTicketBefore.getBalance());
        sellAccountTicketFlow.setAmountAfter(sellAccountTicketBefore.getBalance().add(amount));
        sellAccountTicketFlow.setAddTime(new Date());
        updateCount = tAccountTicketFlowMapper.insertSelective(sellAccountTicketFlow);
        if (updateCount != 1) {
            throw new BizException(ErrorCode.ADD_ERR);
        }
        //更新卖家仓库累计出售数量、累计利润、累计积分
        WareHouseCumulativeDataRequestDto wareHouseCumulativeDataRequestDto = new WareHouseCumulativeDataRequestDto();
        wareHouseCumulativeDataRequestDto.setUserId(sellUserId);
        wareHouseCumulativeDataRequestDto.setAllSell(requestDto.getNum());
        wareHouseCumulativeDataRequestDto.setAllProfit(AmountUtil.profitCalculate(order.getType(),requestDto.getNum()));
        wareHouseCumulativeDataRequestDto.setAllIntegral(AmountUtil.integralCalculate(order.getType()).multiply(new BigDecimal(requestDto.getNum())));
        if (GoodsType.EGG.getType() == order.getType()){
            updateCount = tDuckWarehouseMapper.updateDuckWareHouseCumulativeData(wareHouseCumulativeDataRequestDto);
        }else if (GoodsType.DUCK.getType() == order.getType()){
            updateCount = tEggWarehouseMapper.updateEggWareHouseCumulativeData(wareHouseCumulativeDataRequestDto);
        }
        if (updateCount != 1) {
            throw new BizException(ErrorCode.ADD_ERR);
        }
        //添加日志记录t_notice
        tSellNotice.setUserId(sellUserId);
        tSellNotice.setAddTime(new Date());
        updateCount = tNoticeMapper.insertSelective(tSellNotice);
        if (updateCount != 1) {
            throw new BizException(ErrorCode.ADD_ERR);
        }
        //更新买家仓库表
        BuyGoodsRequestDto buyGoodsRequestDto = new BuyGoodsRequestDto();
        buyGoodsRequestDto.setNum(requestDto.getNum());
        buyGoodsRequestDto.setUserId(buyUserId);
        if (GoodsType.EGG.getType() == order.getType()) {
            TEggWarehouse eggWarehouse = tEggWarehouseMapper.selectByUserId(buyUserId);
            if (eggWarehouse == null) {
                //添加记录
                eggWareHouseServiceImpl.addWareHouse(buyUserId);
            }
            //更新个人仓库记录
            updateCount = tEggWarehouseMapper.updateEggWareHouseBuyDuck(buyGoodsRequestDto);
            if (updateCount != 1) {
                throw new BizException(ErrorCode.UPDATE_ERR);
            }
        } else if (GoodsType.DUCK.getType() == order.getType()) {
            //鸭仓库表，如果没有仓库则新增一条仓库记录
            TDuckWarehouse duckWarehouse = tDuckWarehouseMapper.selectByUserId(buyUserId);
            if (duckWarehouse == null) {
                duckWareHouseServiceImpl.addWareHouse(buyUserId);
            }
            updateCount = tDuckWarehouseMapper.updateDuckWareHouseBuyDuck(buyGoodsRequestDto);
            if (updateCount != 1) {
                throw new BizException(ErrorCode.UPDATE_ERR);
            }
        } else {
            //todo dog
        }
        //更新买家账户表、账户流水表、t_notice
        updateCount = tAccountTicketMapper.updateAccountTicket(updateBuyAccountTicketRequestDto);
        if (updateCount != 1) {
            throw new BizException(ErrorCode.TICKET_NO_ENOUGH);
        }
        buyAccountTicketFlow.setUserId(buyUserId);
        buyAccountTicketFlow.setAmount(amount);
        buyAccountTicketFlow.setAmountBefore(buyAccountTicketBefore.getBalance());
        buyAccountTicketFlow.setAmountAfter(buyAccountTicketBefore.getBalance().subtract(amount));
        buyAccountTicketFlow.setAddTime(new Date());
        updateCount = tAccountTicketFlowMapper.insertSelective(buyAccountTicketFlow);
        if (updateCount != 1) {
            throw new BizException(ErrorCode.ADD_ERR);
        }
        tbuyNotice.setUserId(buyUserId);
        tbuyNotice.setAddTime(new Date());
        updateCount = tNoticeMapper.insertSelective(tbuyNotice);
        if (updateCount != 1) {
            throw new BizException(ErrorCode.ADD_ERR);
        }
        return true;
    }

    /**
     * 发布订单
     *
     * @param requestDto
     * @return
     * @throws BizException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean publishOrders(PublishOrderRequestDto requestDto) throws BizException {
        int updateCount = 0;
        //更新个人仓库表
        if (GoodsType.EGG.getType() == requestDto.getType()) {
            updateCount = tDuckWarehouseMapper.updateDuckWareHouseSellEgg(requestDto);
            if (updateCount != 1) {
                throw new BizException(ErrorCode.EGG_NO_ENOUGH);
            }
        } else if (GoodsType.DUCK.getType() == requestDto.getType()) {
            updateCount = tEggWarehouseMapper.updateEggWareHouseSellDuck(requestDto);
            if (updateCount != 1) {
                throw new BizException(ErrorCode.DUCK_NO_ENOUGH);
            }
        } else {
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
        updateCount = tOrdersMapper.insertSelective(orders);
        if (updateCount != 1) {
            throw new BizException(ErrorCode.ADD_ERR);
        }
        //插入t_notice
        TNotice tNotice = new TNotice();
        tNotice.setUserId(requestDto.getUserId());
        if (GoodsType.EGG.getType() == requestDto.getType()) {
            tNotice.setType(NoticeType.ORDER_SELL_EGG.getType());
            tNotice.setRemarks(NoticeType.ORDER_SELL_EGG.getRemarks().replace("num", requestDto.getNum() + ""));
        } else if (GoodsType.DUCK.getType() == requestDto.getType()) {
            tNotice.setType(NoticeType.ORDER_SELL_DUCK.getType());
            tNotice.setRemarks(NoticeType.ORDER_SELL_DUCK.getRemarks().replace("num", requestDto.getNum() + ""));
        }
        tNotice.setAddTime(new Date());
        updateCount = tNoticeMapper.insertSelective(tNotice);
        if (updateCount != 1) {
            throw new BizException(ErrorCode.ADD_ERR);
        }
        return true;
    }

    /**
     * 购买饲料
     *
     * @param requestDto
     * @return
     * @throws BizException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean buyFeed(BuyGoodsRequestDto requestDto) throws BizException {
        int updateCount = 0;
        TDuckWarehouse duckWarehouse = tDuckWarehouseMapper.selectByUserId(requestDto.getUserId());
        if (duckWarehouse == null) {
            duckWareHouseServiceImpl.addWareHouse(requestDto.getUserId());
        }
        //更新鸭仓库表
        updateCount = tDuckWarehouseMapper.updateDuckWareHouseBuyFeed(requestDto);
        if (updateCount != 1) {
            throw new BizException(ErrorCode.UPDATE_ERR);
        }
        //更新账户表
        TAccountTicket accountTicketBefore = tAccountTicketMapper.queryAccountByUserId(requestDto.getUserId());
        UpdateAccountTicketRequestDto updateAccountTicketRequestDto = new UpdateAccountTicketRequestDto();
        updateAccountTicketRequestDto.setUserId(requestDto.getUserId());
        updateAccountTicketRequestDto.setAcctOpreType(AcctOpreType.BUY_FEED.getType());
        BigDecimal balance = Constant.FEED_PRICE.multiply(new BigDecimal(requestDto.getFeedNum()));
        updateAccountTicketRequestDto.setBalance(balance);
        updateCount = tAccountTicketMapper.updateAccountTicket(updateAccountTicketRequestDto);
        if (updateCount != 1) {
            throw new BizException(ErrorCode.TICKET_NO_ENOUGH);
        }
        //新增账户流水表记录
        TAccountTicketFlow accountTicketFlow = new TAccountTicketFlow();
        accountTicketFlow.setUserId(requestDto.getUserId());
        accountTicketFlow.setType(AcctOpreType.BUY_FEED.getType());
        accountTicketFlow.setAmount(balance);
        accountTicketFlow.setAmountBefore(accountTicketBefore.getBalance());
        accountTicketFlow.setAmountAfter(accountTicketBefore.getBalance().subtract(balance));
        accountTicketFlow.setRemarks("买饲料花费菜票：" + balance);
        accountTicketFlow.setAddTime(new Date());
        updateCount = tAccountTicketFlowMapper.insertSelective(accountTicketFlow);
        if (updateCount < 1) {
            throw new BizException(ErrorCode.ADD_ERR);
        }
        //添加返现记录表
        TBackReward backReward = new TBackReward();
        backReward.setUserId(requestDto.getUserId());
        backReward.setType("3");
        backReward.setNum(requestDto.getFeedNum());
        backReward.setAmount(Constant.FEED_PRICE.multiply(new BigDecimal(requestDto.getFeedNum())));
        backReward.setIfBack("1");
        backReward.setSource(accountTicketFlow.getId());
        backReward.setUpdateTime(new Date());
        backReward.setAddTime(new Date());
        updateCount = tBackRewardMapper.insertSelective(backReward);
        if (updateCount < 1) {
            throw new BizException(ErrorCode.ADD_ERR);
        }
        //添加日志记录t_notice
        TNotice tNotice = new TNotice();
        tNotice.setUserId(requestDto.getUserId());
        tNotice.setType(NoticeType.BUY_FEED.getType());
        tNotice.setRemarks(NoticeType.BUY_FEED.getRemarks().replace("num", requestDto.getFeedNum() + ""));
        tNotice.setAddTime(new Date());
        updateCount = tNoticeMapper.insertSelective(tNotice);
        if (updateCount != 1) {
            throw new BizException(ErrorCode.ADD_ERR);
        }
        return true;
    }

}
