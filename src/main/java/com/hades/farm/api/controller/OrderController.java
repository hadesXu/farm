package com.hades.farm.api.controller;

import com.hades.farm.api.view.ApiResponse;
import com.hades.farm.api.view.response.MsgModel;
import com.hades.farm.api.view.response.OrderIndexModel;
import com.hades.farm.core.data.dto.requestDto.BuyGoodsRequestDto;
import com.hades.farm.core.data.dto.requestDto.OrderQueryRequestDto;
import com.hades.farm.core.data.dto.requestDto.PublishOrderRequestDto;
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

    @RequestMapping(value = "/sellGoods", method = RequestMethod.POST)
    @Auth
    public ApiResponse<MsgModel> sellGoods(@RequestParam long userId,@RequestParam String goodNumStr,@RequestParam String goodTypeStr){
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
            int goodType = Integer.parseInt(goodTypeStr);
            if(goodType == 1){//蛋
                if(goodNum>100 || goodNum<10){
                    msgModel.setCode(ErrorCode.BUY_EGG_LIMIT.getCode());
                    msgModel.setMessage("卖出"+ErrorCode.BUY_EGG_LIMIT.getMessage());
                    response.setResult(msgModel);
                    return response;
                }
            }else if(goodType == 2){
                if(goodNum>100 || goodNum<20){
                    msgModel.setCode(ErrorCode.BUY_DUCK_LIMIT.getCode());
                    msgModel.setMessage("卖出" + ErrorCode.BUY_DUCK_LIMIT.getMessage());
                    response.setResult(msgModel);
                    return response;
                }
            }
            PublishOrderRequestDto requestDto = new PublishOrderRequestDto();
            requestDto.setUserId(userId);
            requestDto.setNum(goodNum);
            requestDto.setType(goodType);
            orderService.publishOrders(requestDto);
        }catch (BizException e){
            msgModel.setCode(e.getErrCode());
            msgModel.setMessage(e.getErrMessage());
        }

        response.setResult(msgModel);
        return response;
    }

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
            int goodType = Integer.parseInt(goodTypeStr);
            long orderId = Long.parseLong(orderIdStr);
            //校验num上限下限
            if(orderIdStr == null || orderId <1){
                if(goodType == 1){//蛋
                    if(goodNum>100 || goodNum<10){
                        msgModel.setCode(ErrorCode.BUY_EGG_LIMIT.getCode());
                        msgModel.setMessage("购买"+ErrorCode.BUY_EGG_LIMIT.getMessage());
                        response.setResult(msgModel);
                        return response;
                    }
                }else if(goodType == 2){
                    if(goodNum>100 || goodNum<20){
                        msgModel.setCode(ErrorCode.BUY_DUCK_LIMIT.getCode());
                        msgModel.setMessage("购买" + ErrorCode.BUY_DUCK_LIMIT.getMessage());
                        response.setResult(msgModel);
                        return response;
                    }
                }
            }
            BuyGoodsRequestDto requestDto = new BuyGoodsRequestDto();
            requestDto.setUserId(userId);
            requestDto.setType(goodType);
            requestDto.setNum(goodNum);
            if(orderIdStr == null || orderId <1){
                if(goodType == 1){
                    orderService.buyEggFromPlatform(requestDto);
                }else if(goodType == 2){
                    orderService.buyDuckFromPlatform(requestDto);
                }else{
                    msgModel.setCode(ErrorCode.GOOD_TYPE_ERROR.getCode());
                    msgModel.setMessage(ErrorCode.GOOD_TYPE_ERROR.getMessage());
                    response.setResult(msgModel);
                    return response;
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
        requestDto.setStatus(1);
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

    @RequestMapping(value = "/buyDoorDogOrRobot", method = RequestMethod.POST)
    @Auth
    public ApiResponse<MsgModel> buyDoorDogOrRobot(@RequestParam long userId,@RequestParam String monthStr,@RequestParam String type){
        ApiResponse<MsgModel> response = new ApiResponse<MsgModel>();
        MsgModel msgModel = new MsgModel(ErrorCode.SUCCESS.getCode(),ErrorCode.SUCCESS.getMessage());
        try {
            //校验month
            ErrorCode errorCode = NumUtil.validateInteger(monthStr);
            if(errorCode.getCode()!=ErrorCode.SUCCESS.getCode()){
                msgModel.setCode(errorCode.getCode());
                msgModel.setMessage(errorCode.getMessage());
                response.setResult(msgModel);
                return response;
            }
            if(!"1".equals(monthStr) && !"2".equals(monthStr) && !"3".equals(monthStr)){
                msgModel.setCode(ErrorCode.NUM_ILLEGAL.getCode());
                msgModel.setMessage(ErrorCode.NUM_ILLEGAL.getMessage());
                response.setResult(msgModel);
                return response;
            }
            BuyGoodsRequestDto requestDto = new BuyGoodsRequestDto();
            requestDto.setNum(Integer.parseInt(monthStr)*30);//天数
            requestDto.setUserId(userId);
            if("1".equals(type)){
                orderService.buyDoorDog(requestDto);
            }else{
                orderService.buyRobot(requestDto);
            }
        }catch (BizException e){
            msgModel.setCode(e.getErrCode());
            msgModel.setMessage(e.getErrMessage());
        }
        response.setResult(msgModel);
        return response;
    }

    /**
     * 支付偷蛋或偷鸭费用
     * @param userId
     * @param type
     * @return
     */
    @RequestMapping(value = "/payStealDuckOrEgg", method = RequestMethod.POST)
    @Auth
    public ApiResponse<MsgModel> payStealDuckOrEgg(@RequestParam long userId,@RequestParam String type){
        ApiResponse<MsgModel> response = new ApiResponse<MsgModel>();
        MsgModel msgModel = new MsgModel(ErrorCode.SUCCESS.getCode(),ErrorCode.SUCCESS.getMessage());
        try {
            ErrorCode errorCode = NumUtil.validateInteger(type);
            if(errorCode.getCode()!=ErrorCode.SUCCESS.getCode()){
                msgModel.setCode(errorCode.getCode());
                msgModel.setMessage(errorCode.getMessage());
                response.setResult(msgModel);
                return response;
            }
             orderService.payStealDuckOrEgg(userId,Integer.parseInt(type));
        }catch (BizException e){
            msgModel.setCode(e.getErrCode());
            msgModel.setMessage(e.getErrMessage());
        }
        response.setResult(msgModel);
        return response;
    }

}
