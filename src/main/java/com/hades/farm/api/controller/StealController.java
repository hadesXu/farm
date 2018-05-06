package com.hades.farm.api.controller;

import com.hades.farm.api.view.*;
import com.hades.farm.api.view.Error;
import com.hades.farm.api.view.response.MsgModel;
import com.hades.farm.api.view.response.StealModel;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.core.service.impl.DuckWareHouseServiceImpl;
import com.hades.farm.core.service.impl.EggWareHouseServiceImpl;
import com.hades.farm.result.ErrorCode;
import com.hades.farm.utils.NickUtil;
import com.langu.authorization.annotation.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by zhengzl on 2018/4/10.
 */
@RestController
@RequestMapping("/api/steal")
public class StealController {

    @Autowired
    private DuckWareHouseServiceImpl duckWareHouseService;

    @Autowired
    private EggWareHouseServiceImpl eggWareHouseService;

    /**
     *
     * @param userId
     * @param offSet
     * @param pageSize
     * @param type 1:蛋，2：鸭
     * @return
     */
    @RequestMapping(value = "/stealList", method = RequestMethod.GET)
    @Auth
    public ApiResponse<List<StealModel>> stealList(@RequestParam long userId,@RequestParam String offSet,@RequestParam String pageSize,@RequestParam String type){
        ApiResponse<List<StealModel>> response = new ApiResponse<List<StealModel>>();
        if(!validateTime()){
            response.setError(new Error(ErrorCode.STEAL_TIME_ERROR.getCode(),ErrorCode.STEAL_TIME_ERROR.getMessage()));
            return response;
        }
        List<StealModel> canStealList = null;
        if("1".equals(type)){
            //偷蛋列表
            canStealList = duckWareHouseService.queryCanStealList(userId,Integer.parseInt(offSet),Integer.parseInt(pageSize));
        }else if("2".equals(type)){
            //偷鸭列表
            canStealList = eggWareHouseService.queryCanStealList(userId,Integer.parseInt(offSet),Integer.parseInt(pageSize));
        }
        if(canStealList!=null && canStealList.size()>0){
            for(StealModel stealModel:canStealList){
                stealModel.setCanStealNum(stealModel.getHarvestNum()/5);
                stealModel.setName(NickUtil.findName(stealModel.getUserId()));
                stealModel.setImgUrl(NickUtil.findImg(stealModel.getUserId()));
            }
        }
        response.setResult(canStealList);
        return response;
    }

    /**
     * 偷鸭
     * @param userId
     * @param targetUserId
     * @return
     */
    @RequestMapping(value = "/stealDuck", method = RequestMethod.GET)
    @Auth
    public ApiResponse<MsgModel> stealDuck(@RequestParam long userId,@RequestParam long targetUserId){
        ApiResponse<MsgModel> response = new ApiResponse<MsgModel>();
        MsgModel msgModel = new MsgModel(ErrorCode.SUCCESS.getCode(),ErrorCode.SUCCESS.getMessage());
        //偷鸭逻辑
        try {
            if(!validateTime()){
                throw  new BizException(ErrorCode.STEAL_TIME_ERROR.getCode(),ErrorCode.STEAL_TIME_ERROR.getMessage());
            }

            if(!eggWareHouseService.checkIsSteal(userId,23)) {
                throw  new BizException(ErrorCode.CANNT_STEAL2.getCode(),ErrorCode.CANNT_STEAL2.getMessage());
            }
            eggWareHouseService.stealDuck(userId, targetUserId);
        }catch (BizException e){
            msgModel.setCode(e.getErrCode());
            msgModel.setMessage(e.getErrMessage());
        }
        response.setResult(msgModel);
        return  response;
    }

    /**
     * 偷蛋
     * @param userId
     * @param targetUserId
     * @return
     */
    @RequestMapping(value = "/stealEgg", method = RequestMethod.GET)
    @Auth
    public ApiResponse<MsgModel> stealEgg(@RequestParam long userId,@RequestParam long targetUserId){
        ApiResponse<MsgModel> response = new ApiResponse<MsgModel>();
        MsgModel msgModel = new MsgModel(ErrorCode.SUCCESS.getCode(),ErrorCode.SUCCESS.getMessage());
        //偷蛋逻辑
        try {
            if(!validateTime()){
                throw  new BizException(ErrorCode.STEAL_TIME_ERROR.getCode(),ErrorCode.STEAL_TIME_ERROR.getMessage());
            }

            if(!eggWareHouseService.checkIsSteal(userId,21)) {
                throw  new BizException(ErrorCode.CANNT_STEAL2.getCode(),ErrorCode.CANNT_STEAL2.getMessage());
            }
            duckWareHouseService.stealEgg(userId, targetUserId);
        }catch (BizException e){
            msgModel.setCode(e.getErrCode());
            msgModel.setMessage(e.getErrMessage());
        }
        response.setResult(msgModel);
        return  response;
    }

    private boolean validateTime(){
        Date now = new Date();
        int hours = now.getHours();
        if(hours>=18 && hours <21){
            return true;
        }else{
            return false;
        }
    }
}
