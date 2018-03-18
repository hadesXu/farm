package com.hades.farm.core.thread;

import com.hades.farm.core.data.entity.TOrders;
import com.hades.farm.core.service.OrderService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 订单返现心跳监听线程
 * Created by xiaoxu on 2018/3/18.
 */
public class OrderTaskHeartMonitorThread implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(OrderTaskHeartMonitorThread.class);

    private OrderService orderService;

    public OrderTaskHeartMonitorThread(OrderService orderService) {
        this.orderService = orderService;
    }

    public OrderTaskHeartMonitorThread() {
        super();
    }

    @Override
    public void run() {
        logger.info("OrderTaskHeartMonitorThread start");
        while (true) {
            try {
                List<TOrders> list = orderService.getUnCachBack();
                if (CollectionUtils.isEmpty(list)) {
                    Thread.sleep(50);
                } else {
                    for (TOrders orders : list) {

                    }
                }
            } catch (Exception e) {
                logger.info("OrderTaskHeartMonitorThread error ");
            }
        }
    }
}
