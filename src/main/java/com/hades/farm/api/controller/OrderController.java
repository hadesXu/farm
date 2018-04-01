package com.hades.farm.api.controller;

import com.hades.farm.api.view.ApiResponse;
import com.hades.farm.api.view.response.MsgModel;
import com.hades.farm.api.view.response.OrderIndexModel;
import com.hades.farm.core.data.dto.requestDto.BuyGoodsRequestDto;
import com.hades.farm.core.data.dto.requestDto.OrderQueryRequestDto;
import com.hades.farm.core.data.dto.resultDto.OrderUserResultDto;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.core.service.OrderQueryService;
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

    @Autowired
    private OrderQueryService orderQueryService;

    @RequestMapping(value = "/buyGoods", method = RequestMethod.POST)
    @Auth
    public ApiResponse<MsgModel> buyGoods (@RequestParam long userId,@RequestParam String goodNumStr,@RequestParam String goodTypeStr,@RequestParam String orderIdStr){
        ApiResponse<MsgModel> response = new ApiResponse<MsgModel>();
        MsgModel msgModel = new MsgModel(ErrorCode.SUCCESS.getCode(),ErrorCode.SUCCESS.getMessage());
        try {
            ErrorCode errorCode = NumUtil.validateInteger(goodNumStr);
            if(errorCode.getCode()!=ErrorCode.SUCCESS.getCode()){
                msgModel.setCode(errorCode.getCode());
                msgModel.setMessage(errorCode.getMessage());
                response.setResult(msgModel);
                return response;
            }
            int goodNum =  Integer.parseInt(goodNumStr);
            int goodType = Integer.parseInt(goodNumStr);
            long orderId = Long.parseLong(orderIdStr);
            BuyGoodsRequestDto requestDto = new BuyGoodsRequestDto();
            requestDto.setUserId(userId);
            requestDto.setType(goodType);
            requestDto.setNum(goodNum);
            if(orderIdStr == null || orderId <1){
                if(goodType == 1){
                    orderService.buyEggFromPlatform(requestDto);
                }else{
                    orderService.buyDuckFromPlatform(requestDto);
                }
            }else{
                requestDto.setOrderId(orderId);
                orderService.buyGoodsFromOrder(requestDto);
            }
        }catch (BizException e){
            msgModel.setCode(e.getErrCode());
            msgModel.setMessage(e.getErrMessage());
        }
        response.setResult(msgModel);
        return response;
    }

    @RequestMapping(value = "/buyFood", method = RequestMethod.POST)
    @Auth
    public ApiResponse<MsgModel> buyFood(@RequestParam long userId,@RequestParam String foodStr){
        ApiResponse<MsgModel> response = new ApiResponse<MsgModel>();
        MsgModel msgModel = new MsgModel(ErrorCode.SUCCESS.getCode(),ErrorCode.SUCCESS.getMessage());
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

    @RequestMapping(value = "/orderIndex", method = RequestMethod.POST)
    @Auth
    public ApiResponse<OrderIndexModel> orderIndex(@RequestParam long userId){
        ApiResponse<OrderIndexModel> response = new ApiResponse<OrderIndexModel>();
        OrderIndexModel orderIndexModel = orderQueryService.orderIndex(userId);
        response.setResult(orderIndexModel);
        return response;
    }

    @RequestMapping(value = "/queryAllOrderList", method = RequestMethod.POST)
    @Auth
    public ApiResponse<List<OrderUserResultDto>> queryAllOrderList(@RequestParam long userId,@RequestParam String offSet,@RequestParam String pageSize){
        ApiResponse<List<OrderUserResultDto>> response = new ApiResponse<List<OrderUserResultDto>>();
        OrderQueryRequestDto requestDto = new OrderQueryRequestDto();
        requestDto.setPageSize(Integer.parseInt(pageSize));
        requestDto.setOffSet(Integer.parseInt(offSet));
        requestDto.setIfExceptSelf(1);//排除自己的记录
        requestDto.setUserId(userId);
        List<OrderUserResultDto> allOrderList = orderQueryService.queryOrderList(requestDto);
        response.setResult(allOrderList);
        return response;
    }

    @RequestMapping(value = "/queryMyOrderList", method = RequestMethod.POST)
    @Auth
    public ApiResponse<List<OrderUserResultDto>> queryMyOrderList(@RequestParam long userId,@RequestParam String offSet,@RequestParam String pageSize){
        ApiResponse<List<OrderUserResultDto>> response = new ApiResponse<List<OrderUserResultDto>>();
        OrderQueryRequestDto requestDto = new OrderQueryRequestDto();
        requestDto.setPageSize(Integer.parseInt(pageSize));
        requestDto.setOffSet(Integer.parseInt(offSet));
        requestDto.setUserId(userId);
        List<OrderUserResultDto> myOrderList = orderQueryService.queryOrderList(requestDto);
        response.setResult(myOrderList);
        return response;
    }



}
