package com.hades.farm.core.service;

import com.hades.farm.core.data.dto.requestDto.BuyGoodsRequestDto;
import com.hades.farm.core.data.dto.requestDto.PublishOrderRequestDto;
import com.hades.farm.core.data.entity.TOrders;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.result.Result;

import java.util.List;

/**
 * Created by zhengzl on 2018/3/10.
 */
public interface OrderService {
    boolean buyFeed(BuyGoodsRequestDto requestDto) throws BizException;

    boolean buyEggFromPlatform(BuyGoodsRequestDto requestDto) throws BizException;

    boolean buyDuckFromPlatform(BuyGoodsRequestDto requestDto) throws BizException;

    boolean buyGoodsFromOrder(BuyGoodsRequestDto requestDto) throws BizException;

    boolean buyDoorDog(BuyGoodsRequestDto requestDto) throws BizException;

    boolean buyRobot(BuyGoodsRequestDto requestDto) throws BizException;

    boolean publishOrders(PublishOrderRequestDto requestDto) throws BizException;

}
