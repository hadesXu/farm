package com.hades.farm.api.controller;

import com.hades.farm.api.view.ApiResponse;
import com.hades.farm.api.view.response.UserModel;
import com.hades.farm.core.data.entity.TDuckWarehouse;
import com.hades.farm.core.service.WareHouseService;
import com.hades.farm.core.service.impl.DuckWareHouseServiceImpl;
import com.langu.authorization.annotation.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengzl on 2018/3/20.
 */
@RestController
@RequestMapping("/api/farm")
public class FarmController {
    @Autowired
    private DuckWareHouseServiceImpl duckWareHouseService;

    @RequestMapping(value = "/myfarm", method = RequestMethod.GET)
    @Auth
    public ApiResponse<Map<String,Object>> myfram(@RequestParam long userId){
        ApiResponse<Map<String,Object>> response = new ApiResponse<Map<String,Object>>();
        Map<String,Object> myFarmMap = new HashMap<>();
        TDuckWarehouse duckWarehouse = duckWareHouseService.queryDuckWareHouse(userId);
        myFarmMap.put("duckWarehouse",duckWarehouse);
        response.setResult(myFarmMap);
        return response;
    }
}
