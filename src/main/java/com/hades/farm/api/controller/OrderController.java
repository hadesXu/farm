package com.hades.farm.api.controller;

import com.hades.farm.api.view.ApiResponse;
import com.hades.farm.api.view.response.MsgModel;
import com.hades.farm.api.view.response.OrderIndexModel;
import com.hades.farm.api.view.response.VendibilityModel;
import com.hades.farm.core.data.dto.requestDto.BuyGoodsRequestDto;
import com.hades.farm.core.data.dto.requestDto.OrderQueryRequestDto;
import com.hades.farm.core.data.dto.requestDto.PublishOrderRequestDto;
import com.hades.farm.core.data.dto.resultDto.OrderUserResultDto;
import com.hades.farm.core.data.entity.TDuckWarehouse;
import com.hades.farm.core.data.entity.TEggWarehouse;
import com.hades.farm.core.data.mapper.TDuckWarehouseMapper;
import com.hades.farm.core.data.mapper.TEggWarehouseMapper;
import com.hades.farm.core.data.mapper.TOrdersMapper;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.core.service.OrderQueryService;
import com.hades.farm.core.service.OrderService;
import com.hades.farm.result.ErrorCode;
import com.hades.farm.utils.Constant;
import com.hades.farm.utils.DateUtils;
import com.hades.farm.utils.NumUtil;
import com.langu.authorization.annotation.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
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

    @Autowired
    private TDuckWarehouseMapper tduckWarehouseMapper;

    @Autowired
    private TEggWarehouseMapper teggWarehouseMapper;

    @Autowired
    private TOrdersMapper tordersMapper;

    @Value("${farm.SUPERVISOR_ID}")
    private String SUPERVISOR_ID;

    @RequestMapping(value = "/sellGoods", method = RequestMethod.POST)
    @Auth
    public ApiResponse<MsgModel> sellGoods(@RequestParam long userId, @RequestParam String goodNumStr, @RequestParam String goodTypeStr) {
        ApiResponse<MsgModel> response = new ApiResponse<MsgModel>();
        MsgModel msgModel = new MsgModel(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage());
        try {

            ErrorCode errorCode = NumUtil.validateInteger(goodNumStr);
            if(!validateTime()){
                msgModel.setCode(ErrorCode.SELL_GOOD_TIME_ERROR.getCode());
                msgModel.setMessage(ErrorCode.SELL_GOOD_TIME_ERROR.getMessage());
                response.setResult(msgModel);
                return response;
            }
            if (errorCode.getCode() != ErrorCode.SUCCESS.getCode()) {
                msgModel.setCode(errorCode.getCode());
                msgModel.setMessage(errorCode.getMessage());
                response.setResult(msgModel);
                return response;
            }
            int goodNum = Integer.parseInt(goodNumStr);
            if(goodNum%10 != 0) {
                msgModel.setCode(ErrorCode.BUY_DUCK_ZS.getCode());
                msgModel.setMessage(ErrorCode.BUY_DUCK_ZS.getMessage());
                response.setResult(msgModel);
                return response;
            }

            String day = DateUtils.dateToString(new Date());
            String start = day.substring(0,10)+" 00:00:00";
            String end = day.substring(0,10)+" 23:59:59";

            int goodType = Integer.parseInt(goodTypeStr);
            if (goodType == 1) {//蛋
                if (goodNum > 200 || goodNum < 10) {
                    msgModel.setCode(ErrorCode.BUY_EGG_LIMIT.getCode());
                    msgModel.setMessage("卖出" + ErrorCode.BUY_EGG_LIMIT.getMessage());
                    response.setResult(msgModel);
                    return response;
                }
                TDuckWarehouse duckHose = tduckWarehouseMapper.selectByUserId(userId);
                if(duckHose.getEgg().intValue() < goodNum) {
                    msgModel.setCode(ErrorCode.PLATFORM_EGG_NO_ENOUGH.getCode());
                    msgModel.setMessage(ErrorCode.PLATFORM_EGG_NO_ENOUGH.getMessage());
                    response.setResult(msgModel);
                    return response;
                }

                OrderQueryRequestDto o1 = new OrderQueryRequestDto();
                o1.setUserId(userId);
                o1.setType(goodType);
                o1.setStartTime(DateUtils.strToDate(start));
                o1.setEndTime(DateUtils.strToDate(end));

                Integer orderEggNum = tordersMapper.queryNumByType(o1);
                if(orderEggNum != null) {
                    if((orderEggNum.intValue()+goodNum) > 600) {
                        msgModel.setCode(ErrorCode.SELL_EGG_LIMIT.getCode());
                        msgModel.setMessage(ErrorCode.SELL_EGG_LIMIT.getMessage());
                        response.setResult(msgModel);
                        return response;
                    }
                }


            } else if (goodType == 2) {
                if (goodNum > 200 || goodNum < 20) {
                    msgModel.setCode(ErrorCode.BUY_DUCK_LIMIT.getCode());
                    msgModel.setMessage("卖出" + ErrorCode.BUY_DUCK_LIMIT.getMessage());
                    response.setResult(msgModel);
                    return response;
                }

                TEggWarehouse eggHose = teggWarehouseMapper.selectByUserId(userId);
                if(eggHose.getDuck().intValue() < goodNum) {
                    msgModel.setCode(ErrorCode.PLATFORM_DUCK_NO_ENOUGH.getCode());
                    msgModel.setMessage(ErrorCode.PLATFORM_DUCK_NO_ENOUGH.getMessage());
                    response.setResult(msgModel);
                    return response;
                }

                OrderQueryRequestDto o1 = new OrderQueryRequestDto();
                o1.setUserId(userId);
                o1.setType(goodType);
                o1.setStartTime(DateUtils.strToDate(start));
                o1.setEndTime(DateUtils.strToDate(end));

                Integer orderDuckNum = tordersMapper.queryNumByType(o1);
                if(orderDuckNum != null) {
                    if((orderDuckNum.intValue()+goodNum) > 200) {
                        msgModel.setCode(ErrorCode.SELL_DUCK_LIMIT.getCode());
                        msgModel.setMessage(ErrorCode.SELL_DUCK_LIMIT.getMessage());
                        response.setResult(msgModel);
                        return response;
                    }
                }

            }
            PublishOrderRequestDto requestDto = new PublishOrderRequestDto();
            requestDto.setUserId(userId);
            requestDto.setNum(goodNum);
            requestDto.setType(goodType);
            orderService.publishOrders(requestDto);
        } catch (BizException e) {
            msgModel.setCode(e.getErrCode());
            msgModel.setMessage(e.getErrMessage());
        }

        response.setResult(msgModel);
        return response;
    }

    @RequestMapping(value = "/buyGoods", method = RequestMethod.POST)
    @Auth
    public ApiResponse<MsgModel> buyGoods(@RequestParam long userId, @RequestParam String goodNumStr, @RequestParam String goodTypeStr, @RequestParam String orderIdStr) {
        ApiResponse<MsgModel> response = new ApiResponse<MsgModel>();
        MsgModel msgModel = new MsgModel(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage());
        try {
            ErrorCode errorCode = NumUtil.validateInteger(goodNumStr);
            if (errorCode.getCode() != ErrorCode.SUCCESS.getCode()) {
                msgModel.setCode(errorCode.getCode());
                msgModel.setMessage(errorCode.getMessage());
                response.setResult(msgModel);
                return response;
            }
            int goodNum = Integer.parseInt(goodNumStr);
            int goodType = Integer.parseInt(goodTypeStr);
            long orderId = Long.parseLong(orderIdStr);
            //校验num上限下限
            if (orderIdStr == null || orderId < 1) {
                //校验是否是super用户
                if(userId != Long.parseLong(SUPERVISOR_ID)){
                    if (goodType == 1) {//蛋
                        if (goodNum > 200 || goodNum < 10) {
                            msgModel.setCode(ErrorCode.BUY_EGG_LIMIT.getCode());
                            msgModel.setMessage("购买" + ErrorCode.BUY_EGG_LIMIT.getMessage());
                            response.setResult(msgModel);
                            return response;
                        }
                    } else if (goodType == 2) {
                        if (goodNum > 200 || goodNum < 20) {
                            msgModel.setCode(ErrorCode.BUY_DUCK_LIMIT.getCode());
                            msgModel.setMessage("购买" + ErrorCode.BUY_DUCK_LIMIT.getMessage());
                            response.setResult(msgModel);
                            return response;
                        }
                    }
                }
            }
            BuyGoodsRequestDto requestDto = new BuyGoodsRequestDto();
            requestDto.setUserId(userId);
            requestDto.setType(goodType);
            requestDto.setNum(goodNum);
            if (orderIdStr == null || orderId < 1) {
                if (goodType == 1) {
                    orderService.buyEggFromPlatform(requestDto);
                } else if (goodType == 2) {
                    orderService.buyDuckFromPlatform(requestDto);
                } else {
                    msgModel.setCode(ErrorCode.GOOD_TYPE_ERROR.getCode());
                    msgModel.setMessage(ErrorCode.GOOD_TYPE_ERROR.getMessage());
                    response.setResult(msgModel);
                    return response;
                }
            } else {
                requestDto.setOrderId(orderId);
                orderService.buyGoodsFromOrder(requestDto);
            }
        } catch (BizException e) {
            msgModel.setCode(e.getErrCode());
            msgModel.setMessage(e.getErrMessage());
        }
        response.setResult(msgModel);
        return response;
    }

    @RequestMapping(value = "/buyFood", method = RequestMethod.POST)
    @Auth
    public ApiResponse<MsgModel> buyFood(@RequestParam long userId, @RequestParam String foodStr) {
        ApiResponse<MsgModel> response = new ApiResponse<MsgModel>();
        MsgModel msgModel = new MsgModel(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage());
        try {
            BuyGoodsRequestDto requestDto = new BuyGoodsRequestDto();
            requestDto.setUserId(userId);
            //校验food
            ErrorCode errorCode = NumUtil.validateInteger(foodStr);
            if (errorCode.getCode() != ErrorCode.SUCCESS.getCode()) {
                msgModel.setCode(errorCode.getCode());
                msgModel.setMessage(errorCode.getMessage());
                response.setResult(msgModel);
                return response;
            }
            requestDto.setFeedNum(Integer.parseInt(foodStr));
            orderService.buyFeed(requestDto);
        } catch (BizException e) {
            msgModel.setCode(e.getErrCode());
            msgModel.setMessage(e.getErrMessage());
        }
        response.setResult(msgModel);
        return response;
    }

    @RequestMapping(value = "/orderIndex", method = RequestMethod.POST)
    @Auth
    public ApiResponse<OrderIndexModel> orderIndex(@RequestParam long userId) {
        ApiResponse<OrderIndexModel> response = new ApiResponse<OrderIndexModel>();
        OrderIndexModel orderIndexModel = orderQueryService.orderIndex(userId);
        response.setResult(orderIndexModel);
        return response;
    }

    @RequestMapping(value = "/vendibility", method = RequestMethod.GET)
    @Auth
    public ApiResponse<VendibilityModel> findVendibility(@RequestParam long userId) {
        ApiResponse<VendibilityModel> response = new ApiResponse<VendibilityModel>();
        VendibilityModel vendibility = orderQueryService.findVendibility(userId);
        response.setResult(vendibility);
        return response;
    }

    @RequestMapping(value = "/queryAllOrderList", method = RequestMethod.POST)
    @Auth
    public ApiResponse<List<OrderUserResultDto>> queryAllOrderList(@RequestParam long userId, @RequestParam String offSet, @RequestParam String pageSize) {
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
    public ApiResponse<List<OrderUserResultDto>> queryMyOrderList(@RequestParam long userId, @RequestParam String offSet, @RequestParam String pageSize) {
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
    public ApiResponse<MsgModel> buyDoorDogOrRobot(@RequestParam long userId, @RequestParam String monthStr, @RequestParam String type) {
        ApiResponse<MsgModel> response = new ApiResponse<MsgModel>();
        MsgModel msgModel = new MsgModel(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage());
        try {
            //校验month
            ErrorCode errorCode = NumUtil.validateInteger(monthStr);
            if (errorCode.getCode() != ErrorCode.SUCCESS.getCode()) {
                msgModel.setCode(errorCode.getCode());
                msgModel.setMessage(errorCode.getMessage());
                response.setResult(msgModel);
                return response;
            }
            if (!"1".equals(monthStr) && !"2".equals(monthStr) && !"3".equals(monthStr)) {
                msgModel.setCode(ErrorCode.NUM_ILLEGAL.getCode());
                msgModel.setMessage(ErrorCode.NUM_ILLEGAL.getMessage());
                response.setResult(msgModel);
                return response;
            }
            BuyGoodsRequestDto requestDto = new BuyGoodsRequestDto();
            requestDto.setNum(Integer.parseInt(monthStr) * 30);//天数
            requestDto.setUserId(userId);
            if ("1".equals(type)) {
                orderService.buyDoorDog(requestDto);
            } else {
                orderService.buyRobot(requestDto);
            }
        } catch (BizException e) {
            msgModel.setCode(e.getErrCode());
            msgModel.setMessage(e.getErrMessage());
        }
        response.setResult(msgModel);
        return response;
    }

    /**
     * 支付偷蛋或偷鸭费用
     *
     * @param userId
     * @param type
     * @return
     */
    @RequestMapping(value = "/payStealDuckOrEgg", method = RequestMethod.POST)
    @Auth
    public ApiResponse<MsgModel> payStealDuckOrEgg(@RequestParam long userId, @RequestParam String type) {
        ApiResponse<MsgModel> response = new ApiResponse<MsgModel>();
        MsgModel msgModel = new MsgModel(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage());
        try {
            ErrorCode errorCode = NumUtil.validateInteger(type);
            if (errorCode.getCode() != ErrorCode.SUCCESS.getCode()) {
                msgModel.setCode(errorCode.getCode());
                msgModel.setMessage(errorCode.getMessage());
                response.setResult(msgModel);
                return response;
            }
            orderService.payStealDuckOrEgg(userId, Integer.parseInt(type));
        } catch (BizException e) {
            msgModel.setCode(e.getErrCode());
            msgModel.setMessage(e.getErrMessage());
        }
        response.setResult(msgModel);
        return response;
    }

    private boolean validateTime(){
        Date now = new Date();
        int hours = now.getHours();
        int minutes = now.getMinutes();
        if(hours>=9 && (hours <17 || (hours==17 && minutes<=30))){
            return true;
        }else{
            return false;
        }
    }

}
