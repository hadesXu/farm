package com.hades.farm.core.service;

import com.hades.farm.core.data.entity.TPayItem;
import com.hades.farm.result.Result;

import java.util.List;

/**
 * Created by xiaoxu on 2018/4/6.
 */
public interface RechargeService {

    /**
     * 获取商品列表
     *
     * @return
     */
    Result<List<TPayItem>> getItem();

    /**
     * 下单
     *
     * @param userId
     * @param itemId
     * @return
     */
    Result<Void> placeOrder(long userId, long itemId);
}
