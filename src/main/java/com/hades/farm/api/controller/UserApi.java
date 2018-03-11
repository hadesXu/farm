package com.hades.farm.api.controller;

import com.hades.farm.api.convert.UserConverter;
import com.hades.farm.api.view.ApiResponse;
import com.hades.farm.api.view.request.RegisterRequest;
import com.hades.farm.core.data.entity.User;
import com.hades.farm.core.service.UserService;
import com.hades.farm.result.Result;
import com.hades.farm.api.view.response.UserModel;
import com.langu.authorization.annotation.Auth;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by xiaoxu on 2016/11/9.
 */
@RestController
@RequestMapping("/api/user")
public class UserApi {

    @Resource
    private UserService userService;
    @Resource
    private UserConverter userConverter;

    @PostMapping("/register")
    public ApiResponse<UserModel> login(RegisterRequest request) {
        ApiResponse<UserModel> response = new ApiResponse<>();
        Result<User> registerRes = userService.userRegister(request);
        if (!registerRes.isSuccess()) {
            response.addError(registerRes.getErrorCodes());
            return response;
        }
        response.setResult(userConverter.convert(registerRes.getData(), true));
        return response;
    }

    @GetMapping("/login")
    public ApiResponse<UserModel> login(@RequestParam(required = false, defaultValue = "0") String phone,
                                        @RequestParam(required = false, defaultValue = "0") String pwd) {
        ApiResponse<UserModel> response = new ApiResponse<>();
        Result<User> loginRes = userService.login(phone, pwd);
        if (!loginRes.isSuccess()) {
            response.addError(loginRes.getErrorCodes());
            return response;
        }
        response.setResult(userConverter.convert(loginRes.getData(), true));
        return response;
    }

    @GetMapping("/login/weChat")
    public ApiResponse<UserModel> login(@RequestParam(required = false, defaultValue = "0") String wechat) {
        ApiResponse<UserModel> response = new ApiResponse<>();
        Result<User> loginRes = userService.login(wechat);
        if (!loginRes.isSuccess()) {
            response.addError(loginRes.getErrorCodes());
            return response;
        }
        response.setResult(userConverter.convert(loginRes.getData(), true));
        return response;
    }

    @GetMapping("/user/auto/login")
    @Auth
    public ApiResponse<UserModel> autoLogin(@RequestParam long userId) {
        ApiResponse<UserModel> response = new ApiResponse<>();
        Result<User> loginRes = userService.userAutoLogin(userId);
        if (!loginRes.isSuccess()) {
            response.addError(loginRes.getErrorCodes());
            return response;
        }
        response.setResult(userConverter.convert(loginRes.getData(), false));
        return response;
    }
}