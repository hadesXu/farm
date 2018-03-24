package com.hades.farm.api.controller;

import com.hades.farm.api.view.ApiResponse;
import com.hades.farm.api.view.response.UserModel;
import com.hades.farm.api.view.response.YjcInfoModel;
import com.hades.farm.core.data.entity.TDuckWarehouse;
import com.hades.farm.core.data.entity.TNotice;
import com.hades.farm.core.data.mapper.TDuckWarehouseMapper;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.core.service.NoticeService;
import com.hades.farm.core.service.WareHouseService;
import com.hades.farm.core.service.impl.DuckWareHouseServiceImpl;
import com.hades.farm.core.service.impl.FarmService;
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


    @RequestMapping(value = "/myfarm", method = RequestMethod.GET)
    @Auth
    public ApiResponse<List<TNotice>> myfram(@RequestParam long userId){
        ApiResponse<List<TNotice>> response = new ApiResponse<List<TNotice>>();
        /*Map<String,Object> myFarmMap = new HashMap<>();
        TDuckWarehouse duckWarehouse = duckWareHouseService.queryDuckWareHouse(userId);
        myFarmMap.put("duckWarehouse",duckWarehouse);
        response.setResult(myFarmMap);*/
        //读取最近num条notice记录
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
}
