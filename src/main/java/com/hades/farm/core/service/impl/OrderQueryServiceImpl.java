package com.hades.farm.core.service.impl;

import com.hades.farm.api.view.response.OrderIndexModel;
import com.hades.farm.core.data.dto.requestDto.OrderQueryRequestDto;
import com.hades.farm.core.data.dto.resultDto.OrderUserResultDto;
import com.hades.farm.core.data.entity.TDuckWarehouse;
import com.hades.farm.core.data.entity.TEggWarehouse;
import com.hades.farm.core.data.entity.TOrders;
import com.hades.farm.core.data.entity.TPlatformWarehouse;
import com.hades.farm.core.data.mapper.*;
import com.hades.farm.core.service.OrderQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhengzl on 2018/3/25.
 */
@Service
public class OrderQueryServiceImpl implements OrderQueryService {
    @Autowired
    private TOrdersMapper tOrdersMapper;

    @Autowired
    private TNoticeMapper tNoticeMapper;

    @Autowired
    private TPlatformWarehouseMapper tPlatformWarehouseMapper;
    @Autowired
    private TDuckWarehouseMapper tDuckWarehouseMapper;
    @Autowired
    private TEggWarehouseMapper tEggWarehouseMapper;

    @Override
    public List<OrderUserResultDto> queryOrderList(OrderQueryRequestDto requestDto){
        List<OrderUserResultDto> orderList = tOrdersMapper.queryOrderListByCondition(requestDto);
        return orderList;
    }
    @Override
    public OrderIndexModel orderIndex(long userId){
        OrderIndexModel orderIndexModel = new OrderIndexModel();
        OrderQueryRequestDto allOrderCondition = new OrderQueryRequestDto();
        allOrderCondition.setOffSet(0);
        allOrderCondition.setPageSize(4);
        allOrderCondition.setIfExceptSelf(1);//排除自己的记录
        allOrderCondition.setUserId(userId);
        orderIndexModel.setAllOrderList(tOrdersMapper.queryOrderListByCondition(allOrderCondition));
        OrderQueryRequestDto myOrderCondition = new OrderQueryRequestDto();
        myOrderCondition.setUserId(userId);
        myOrderCondition.setOffSet(0);
        myOrderCondition.setPageSize(3);
        orderIndexModel.setMyOrderList(tOrdersMapper.queryOrderListByCondition(myOrderCondition));
        orderIndexModel.setNoticeList(tNoticeMapper.queryNoticeWithUser(null, 5));
        TPlatformWarehouse platformWarehouse = tPlatformWarehouseMapper.queryPlatformWarehouse();
        orderIndexModel.setPlatformWarehouse(platformWarehouse);
        //查询商品鸭商品蛋的数量
        TDuckWarehouse duckWarehouse = tDuckWarehouseMapper.selectByUserId(userId);
        TEggWarehouse eggWarehouse  = tEggWarehouseMapper.selectByUserId(userId);
        orderIndexModel.setDuckNum(eggWarehouse==null?0:eggWarehouse.getDuck());
        orderIndexModel.setEggNum(duckWarehouse==null?0:duckWarehouse.getEgg());
        return orderIndexModel;
    }
}
