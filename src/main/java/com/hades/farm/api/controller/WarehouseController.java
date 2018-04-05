package com.hades.farm.api.controller;

import com.hades.farm.api.view.ApiResponse;
import com.hades.farm.api.view.response.MsgModel;
import com.hades.farm.api.view.response.MyWarehouseModel;
import com.hades.farm.core.service.WarehouseQueryService;
import com.langu.authorization.annotation.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhengzl on 2018/4/5.
 */
@RestController
@RequestMapping("/api/warehouse")
public class WarehouseController {
    @Autowired
    private WarehouseQueryService warehouseQueryService;

    /**
     * 我的仓库
     * @param userId
     * @return
     */
    @RequestMapping(value = "/mywarehouse", method = RequestMethod.GET)
    @Auth
    public ApiResponse<MyWarehouseModel> mywarehouse(@RequestParam long userId){
        ApiResponse<MyWarehouseModel> response = new ApiResponse<MyWarehouseModel>();
        MyWarehouseModel myWarehouseModel = warehouseQueryService.mywarehouse(userId);
        response.setResult(myWarehouseModel);
        return response;
    }
}
