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

    @RequestMapping(value = "/register", method = RequestMethod.POST)
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

    @RequestMapping(value = "/login", method = RequestMethod.GET)
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

    @RequestMapping(value = "/login/weChat", method = RequestMethod.GET)
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

    @RequestMapping(value = "/auto/login", method = RequestMethod.GET)
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

    @RequestMapping(value = "/update/pwd", method = RequestMethod.POST)
    public ApiResponse login(@RequestParam(required = false, defaultValue = "") String phone,
                             @RequestParam(required = false, defaultValue = "") String code,
                             @RequestParam(required = false, defaultValue = "") String pwd) {
        ApiResponse response = new ApiResponse<>();
        Result<Void> registerRes = userService.updatePwd(phone, code, pwd);
        if (!registerRes.isSuccess()) {
            response.addError(registerRes.getErrorCodes());
            return response;
        }
        return response;
    }
}
