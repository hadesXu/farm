package com.hades.farm.api.controller;

import com.hades.farm.api.view.ApiResponse;
import com.hades.farm.api.view.response.MsgModel;
import com.hades.farm.core.data.dto.requestDto.BuyGoodsRequestDto;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.core.service.OrderService;
import com.hades.farm.result.ErrorCode;
import com.hades.farm.utils.NumUtil;
import com.langu.authorization.annotation.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zhengzl on 2018/3/24.
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/buyFood", method = RequestMethod.POST)
    @Auth
    public ApiResponse<MsgModel> buyFood(@RequestParam long userId,@RequestParam String foodStr){
        ApiResponse<MsgModel> response = new ApiResponse<MsgModel>();
        MsgModel msgModel = new MsgModel();
        try {
            BuyGoodsRequestDto requestDto = new BuyGoodsRequestDto();
            requestDto.setUserId(userId);
            //校验food
            ErrorCode errorCode = NumUtil.validateInteger(foodStr);
            if(errorCode.getCode()!=ErrorCode.SUCCESS.getCode()){
                msgModel.setCode(errorCode.getCode());
                msgModel.setMessage(errorCode.getMessage());
                response.setResult(msgModel);
                return response;
            }
            requestDto.setFeedNum(Integer.parseInt(foodStr));
           orderService.buyFeed(requestDto);
        }catch (BizException e){
            msgModel.setCode(e.getErrCode());
            msgModel.setMessage(e.getErrMessage());
        }
        response.setResult(msgModel);
        return response;
    }

}
