package com.hades.farm.api.controller;

import com.hades.farm.api.view.ApiResponse;
import com.hades.farm.api.view.response.FdcInfoModel;
import com.hades.farm.api.view.response.MsgModel;
import com.hades.farm.api.view.response.YjcInfoModel;
import com.hades.farm.core.data.dto.requestDto.BreedingRequestDto;
import com.hades.farm.core.data.entity.TNotice;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.core.service.DuckBreedingService;
import com.hades.farm.core.service.EggBreedingService;
import com.hades.farm.core.service.NoticeService;
import com.hades.farm.core.service.impl.DuckWareHouseServiceImpl;
import com.hades.farm.core.service.FarmService;
import com.hades.farm.result.ErrorCode;
import com.hades.farm.utils.NumUtil;
import com.langu.authorization.annotation.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zhengzl on 2018/3/20.
 */
@RestController
@RequestMapping("/api/farm")
public class FarmController {
    @Autowired
    private NoticeService noticeService;

    @Autowired
    private FarmService farmService;

    @Autowired
    private DuckBreedingService duckBreedingService;

    @Autowired
    private EggBreedingService eggBreedingService;


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
     * 收获
     * @param userId
     * @param goodTypeStr
     * @return
     */
    @RequestMapping(value = "/shouhuo", method = RequestMethod.POST)
    @Auth
    public ApiResponse<MsgModel> shouhuo(@RequestParam long userId,@RequestParam String goodTypeStr){
        ApiResponse<MsgModel> response = new ApiResponse<MsgModel>();
        MsgModel msgModel = new MsgModel(ErrorCode.SUCCESS.getCode(),ErrorCode.SUCCESS.getMessage());
        try {
             farmService.shouhuo(userId,goodTypeStr);
        } catch (BizException e){
            msgModel.setCode(e.getErrCode());
            msgModel.setMessage(e.getErrMessage());
        }
        response.setResult(msgModel);
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
            if(duckNumInt>100 || duckNumInt<20){
                msgModel.setCode(ErrorCode.BUY_DUCK_LIMIT.getCode());
                msgModel.setMessage("放养"+ErrorCode.BUY_DUCK_LIMIT.getMessage());
                response.setResult(msgModel);
                return response;
            }
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

    @RequestMapping(value = "/fdcInfo", method = RequestMethod.GET)
    @Auth
    public ApiResponse<FdcInfoModel> fdcInfo(@RequestParam long userId){
        ApiResponse<FdcInfoModel>  response = new ApiResponse<FdcInfoModel>();
        FdcInfoModel fdcInfoModel = null;
        try {
            fdcInfoModel = farmService.queryFdcInfo(userId);
        }catch (BizException e){
            e.printStackTrace();
        }catch (Exception e1){
            e1.printStackTrace();
        }
        response.setResult(fdcInfoModel);
        return response;
    }

    /**
     *
     * @param userId
     * @param warmWho:1-自己，2：师傅
     * @return
     */
    @RequestMapping(value = "/warm", method = RequestMethod.POST)
    @Auth
    public ApiResponse<MsgModel> warm(@RequestParam long userId,@RequestParam String warmWho){
        ApiResponse<MsgModel> response = new ApiResponse<MsgModel>();
        MsgModel msgModel = new MsgModel(ErrorCode.SUCCESS.getCode(),ErrorCode.SUCCESS.getMessage());
        try {
            if("1".equals(warmWho)){
                eggBreedingService.warmSelf(userId);
            }else{
                eggBreedingService.warmMaster(userId);
            }
        } catch (BizException e){
            msgModel.setCode(e.getErrCode());
            msgModel.setMessage(e.getErrMessage());
        }
        response.setResult(msgModel);
        return response;
    }

    @RequestMapping(value = "/eggBreeding", method = RequestMethod.POST)
    @Auth
    public ApiResponse<MsgModel> eggBreeding(@RequestParam long userId,@RequestParam String eggNum){
        ApiResponse<MsgModel> response = new ApiResponse<MsgModel>();
        MsgModel msgModel = new MsgModel(ErrorCode.SUCCESS.getCode(),ErrorCode.SUCCESS.getMessage());
        try {
            BreedingRequestDto requestDto = new BreedingRequestDto();
            requestDto.setUserId(userId);
            //校验duckNum
            ErrorCode errorCode = NumUtil.validateInteger(eggNum);
            if(errorCode.getCode()!=ErrorCode.SUCCESS.getCode()){
                msgModel.setCode(errorCode.getCode());
                msgModel.setMessage(errorCode.getMessage());
                response.setResult(msgModel);
                return response;
            }
            int eggNumInt = Integer.parseInt(eggNum);
            requestDto.setNum(eggNumInt);
            if(eggNumInt>100 || eggNumInt<10){
                msgModel.setCode(ErrorCode.BUY_EGG_LIMIT.getCode());
                msgModel.setMessage("孵化"+ErrorCode.BUY_EGG_LIMIT.getMessage());
                response.setResult(msgModel);
                return response;
            }
            if(eggNumInt<1 || eggNumInt%10!=0){
                msgModel.setCode(errorCode.getCode());
                msgModel.setMessage(errorCode.getMessage());
                response.setResult(msgModel);
                return response;
            }
            eggBreedingService.breeding(requestDto);
        }catch (BizException e){
            msgModel.setCode(e.getErrCode());
            msgModel.setMessage(e.getErrMessage());
        }
        response.setResult(msgModel);
        return response;
    }
}
