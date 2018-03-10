package com.hades.farm.test.service;

import com.hades.farm.core.data.dto.requestDto.BuyGoodsRequestDto;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.core.service.OrderService;
import com.hades.farm.core.service.impl.OrderServiceImpl;
import com.hades.farm.enums.GoodsType;
import com.hades.farm.test.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Created by zhengzl on 2018/3/10.
 */
public class OrderServiceTest  extends BaseTest {
    @Resource
    OrderService orderService;

    @Test
    public void buyDuckFromPlatformTest()throws BizException{
        BuyGoodsRequestDto requestDto = new  BuyGoodsRequestDto();
        requestDto.setUserId(1);
        requestDto.setNum(20);
        requestDto.setType(GoodsType.DUCK.getType());
        try {
            orderService.buyDuckFromPlatform(requestDto);
        }catch (BizException e){
            System.out.println("errorCode:" +e.getErrCode()+"errorMsg:"+e.getErrMessage());
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void buyFeed() throws BizException{
        BuyGoodsRequestDto requestDto = new  BuyGoodsRequestDto();
        requestDto.setUserId(1);
        requestDto.setFeedNum(new BigDecimal("5"));
        requestDto.setType(GoodsType.FEED.getType());
        try {
            orderService.buyFeed(requestDto);
        }catch (BizException e){
            System.out.println("errorCode:" +e.getErrCode()+"errorMsg:"+e.getErrMessage());
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
