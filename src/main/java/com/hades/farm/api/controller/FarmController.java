package com.hades.farm.api.controller;

import com.hades.farm.api.view.ApiResponse;
import com.hades.farm.api.view.response.UserModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhengzl on 2018/3/20.
 */
@RestController
@RequestMapping("/api/farm")
public class FarmController {
    @RequestMapping(value = "/myfarm", method = RequestMethod.POST)
    public String myfram(){
       return "myfarm";
    }
}
