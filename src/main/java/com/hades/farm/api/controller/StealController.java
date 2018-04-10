package com.hades.farm.api.controller;

import com.hades.farm.api.view.ApiResponse;
import com.hades.farm.api.view.response.MsgModel;
import com.hades.farm.api.view.response.StealModel;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.core.service.impl.DuckWareHouseServiceImpl;
import com.hades.farm.core.service.impl.EggWareHouseServiceImpl;
import com.hades.farm.result.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zhengzl on 2018/4/10.
 */
@RestController
@RequestMapping("/api/farm")
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
    public ApiResponse<List<StealModel>> stealDuckList(@RequestParam long userId,@RequestParam String offSet,@RequestParam String pageSize,@RequestParam String type){
        List<StealModel> canStealDuckList = null;
        if("1".equals(type)){
            //偷蛋列表
            canStealDuckList = duckWareHouseService.queryCanStealList(userId,Integer.parseInt(offSet),Integer.parseInt(pageSize));
        }else if("2".equals(type)){
            //偷鸭列表
            canStealDuckList = eggWareHouseService.queryCanStealList(userId,Integer.parseInt(offSet),Integer.parseInt(pageSize));
        }
        if(canStealDuckList!=null && canStealDuckList.size()>0){
            for(StealModel stealModel:canStealDuckList){
                stealModel.setCanStealNum(stealModel.getHarvestNum()/5);
            }
        }
        ApiResponse<List<StealModel>> response = new ApiResponse<List<StealModel>>();
        response.setResult(canStealDuckList);
        return response;
    }

    /**
     * 偷鸭
     * @param userId
     * @param targetUserId
     * @return
     */
    public ApiResponse<MsgModel> stealDuck(@RequestParam long userId,@RequestParam long targetUserId){
        ApiResponse<MsgModel> response = new ApiResponse<MsgModel>();
        MsgModel msgModel = new MsgModel(ErrorCode.SUCCESS.getCode(),ErrorCode.SUCCESS.getMessage());
        //TODO 偷鸭逻辑

        response.setResult(msgModel);
        return  response;
    }

    /**
     * 偷蛋
     * @param userId
     * @param targetUserId
     * @return
     */
    public ApiResponse<MsgModel> stealEgg(@RequestParam long userId,@RequestParam long targetUserId){
        ApiResponse<MsgModel> response = new ApiResponse<MsgModel>();
        MsgModel msgModel = new MsgModel(ErrorCode.SUCCESS.getCode(),ErrorCode.SUCCESS.getMessage());
        //TODO 偷蛋逻辑
        try {
            duckWareHouseService.stealEgg(userId,targetUserId);
        }catch (BizException e){
            msgModel.setCode(e.getErrCode());
            msgModel.setMessage(e.getErrMessage());
        }
        response.setResult(msgModel);
        return  response;
    }
}
