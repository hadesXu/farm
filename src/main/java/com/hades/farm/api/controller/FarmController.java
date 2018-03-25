package com.hades.farm.api.controller;

import com.hades.farm.api.view.ApiResponse;
import com.hades.farm.api.view.response.MsgModel;
import com.hades.farm.api.view.response.UserModel;
import com.hades.farm.api.view.response.YjcInfoModel;
import com.hades.farm.core.data.dto.requestDto.BreedingRequestDto;
import com.hades.farm.core.data.entity.TDuckWarehouse;
import com.hades.farm.core.data.entity.TNotice;
import com.hades.farm.core.data.mapper.TDuckWarehouseMapper;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.core.service.DuckBreedingService;
import com.hades.farm.core.service.NoticeService;
import com.hades.farm.core.service.WareHouseService;
import com.hades.farm.core.service.impl.DuckWareHouseServiceImpl;
import com.hades.farm.core.service.impl.FarmService;
import com.hades.farm.result.ErrorCode;
import com.hades.farm.utils.NumUtil;
import com.langu.authorization.annotation.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengzl on 2018/3/20.
 */
@RestController
@RequestMapping("/api/farm")
public class FarmController {
    @Autowired
    private DuckWareHouseServiceImpl duckWareHouseService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private FarmService farmService;

    @Autowired
    private DuckBreedingService duckBreedingService;


    @RequestMapping(value = "/myfarm", method = RequestMethod.GET)
    @Auth
    public ApiResponse<List<TNotice>> myfram(@RequestParam long userId){
        ApiResponse<List<TNotice>> response = new ApiResponse<List<TNotice>>();
        int num = 5;
        List<TNotice> noticeList = noticeService.getNumNotice(userId,num);
        response.setResult(noticeList);
        return response;
    }

    @RequestMapping(value = "/yjcInfo", method = RequestMethod.GET)
    @Auth
    public ApiResponse<YjcInfoModel> yjcInfo(@RequestParam long userId){
        YjcInfoModel yjcInfoModel = null;
        ApiResponse<YjcInfoModel> response = new ApiResponse<YjcInfoModel>();
        try {
            yjcInfoModel = farmService.queryYjcInfo(userId);
        }catch (BizException e){
            e.printStackTrace();
        }catch (Exception e1){
            e1.printStackTrace();
        }
        response.setResult(yjcInfoModel);
        return response;
    }

    /**
     * 养鸭
     * @param userId
     * @param duckNum
     * @return
     */
    @RequestMapping(value = "/duckBreeding", method = RequestMethod.POST)
    @Auth
    public ApiResponse<MsgModel> duckBreeding(@RequestParam long userId,@RequestParam String duckNum){
        ApiResponse<MsgModel> response = new ApiResponse<MsgModel>();
        MsgModel msgModel = new MsgModel(ErrorCode.SUCCESS.getCode(),ErrorCode.SUCCESS.getMessage());
        try {
            BreedingRequestDto requestDto = new BreedingRequestDto();
            requestDto.setUserId(userId);
            //校验duckNum
            ErrorCode errorCode = NumUtil.validateInteger(duckNum);
            if(errorCode.getCode()!=ErrorCode.SUCCESS.getCode()){
                msgModel.setCode(errorCode.getCode());
                msgModel.setMessage(errorCode.getMessage());
                response.setResult(msgModel);
                return response;
            }
            int duckNumInt = Integer.parseInt(duckNum);
            requestDto.setNum(duckNumInt);
            if(duckNumInt<1 || duckNumInt%10!=0){
                msgModel.setCode(errorCode.getCode());
                msgModel.setMessage(errorCode.getMessage());
                response.setResult(msgModel);
                return response;
            }
            duckBreedingService.breeding(requestDto);
        }catch (BizException e){
            msgModel.setCode(e.getErrCode());
            msgModel.setMessage(e.getErrMessage());
        }
        response.setResult(msgModel);
        return response;
    }

    @RequestMapping(value = "/feeding", method = RequestMethod.POST)
    @Auth
    public ApiResponse<MsgModel> feeding(@RequestParam long userId){
        ApiResponse<MsgModel> response = new ApiResponse<MsgModel>();
        MsgModel msgModel = new MsgModel(ErrorCode.SUCCESS.getCode(),ErrorCode.SUCCESS.getMessage());
        try {
            duckBreedingService.feeding(userId);
        } catch (BizException e){
            msgModel.setCode(e.getErrCode());
            msgModel.setMessage(e.getErrMessage());
        }
        response.setResult(msgModel);
        return response;
    }
}
