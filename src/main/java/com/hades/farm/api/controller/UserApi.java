package com.hades.farm.api.controller;

import com.hades.farm.api.view.ApiResponse;
import com.hades.farm.core.data.entity.User;
import com.hades.farm.core.service.UserService;
import com.hades.farm.result.Result;
import com.hades.farm.api.view.response.UserModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by xiaoxu on 2016/11/9.
 */
@RestController
@RequestMapping("/api")
public class UserApi {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/user/getUserById", method = RequestMethod.POST)
//    @Auth
    public ApiResponse getUserById(@RequestParam long userId) {
        ApiResponse<UserModel> result = new ApiResponse<>();
        Result<User> uRes = userService.get(userId);
        if (!uRes.isSuccess()) {
            result.addError(uRes.getErrorCodes());
            return result;
        }
        return result;
    }
}
