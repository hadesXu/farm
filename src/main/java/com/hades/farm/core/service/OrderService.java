package com.hades.farm.core.service;

import com.hades.farm.core.data.dto.requestDto.BuyGoodsRequestDto;
import com.hades.farm.core.data.dto.requestDto.PublishOrderRequestDto;
import com.hades.farm.core.exception.BizException;

/**
 * Created by zhengzl on 2018/3/10.
 */
public interface OrderService {
    public boolean buyFeed(BuyGoodsRequestDto requestDto) throws BizException;

    public boolean buyEggFromPlatform(BuyGoodsRequestDto requestDto) throws BizException;

    public boolean buyDuckFromPlatform(BuyGoodsRequestDto requestDto) throws BizException;

    public boolean buyGoodsFromOrder(BuyGoodsRequestDto requestDto) throws BizException;

    public boolean buyDoorDog(BuyGoodsRequestDto requestDto) throws BizException;

    public boolean buyRobot(BuyGoodsRequestDto requestDto) throws BizException;

    public boolean publishOrders(PublishOrderRequestDto requestDto) throws BizException;
}
