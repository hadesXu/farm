package com.hades.farm.api.controller;

import com.hades.farm.api.convert.UserConverter;
import com.hades.farm.api.view.ApiResponse;
import com.hades.farm.api.view.request.RegisterRequest;
import com.hades.farm.api.view.response.HomeInfoModel;
import com.hades.farm.core.data.entity.TAccountIntegral;
import com.hades.farm.core.data.entity.TAccountTicket;
import com.hades.farm.core.data.entity.TNotice;
import com.hades.farm.core.data.entity.User;
import com.hades.farm.core.service.AccountService;
import com.hades.farm.core.service.NoticeService;
import com.hades.farm.core.service.UserService;
import com.hades.farm.result.Result;
import com.hades.farm.api.view.response.UserModel;
import com.langu.authorization.annotation.Auth;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xiaoxu on 2016/11/9.
 */
@RestController
@RequestMapping("/api/user")
public class UserApi {

    @Resource
    private UserService userService;
    @Resource
    private NoticeService noticeService;
    @Resource
    private UserConverter userConverter;
    @Resource
    private AccountService accountService;

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
    public ApiResponse<UserModel> loginWeChat(@RequestParam(required = false, defaultValue = "") String wechat,
                                              @RequestParam(required = false, defaultValue = "") String name,
                                              @RequestParam(required = false, defaultValue = "") String imgUrl) {
        ApiResponse<UserModel> response = new ApiResponse<>();
        Result<User> loginRes = userService.login(wechat, name, imgUrl);
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

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ApiResponse<UserModel> get(@RequestParam(required = false, defaultValue = "0") long userId) {
        ApiResponse<UserModel> response = new ApiResponse<>();
        Result<User> loginRes = userService.get(userId);
        if (!loginRes.isSuccess()) {
            response.addError(loginRes.getErrorCodes());
            return response;
        }
        response.setResult(userConverter.convert(loginRes.getData(), false));
        return response;
    }

    @RequestMapping(value = "/notice", method = RequestMethod.GET)
    @Auth
    public ApiResponse getNotice(@RequestParam long userId,
                                 @RequestParam(required = false, defaultValue = "1") int page,
                                 @RequestParam(required = false, defaultValue = "20") int num) {
        ApiResponse response = new ApiResponse<>();
        Result<List<TNotice>> result = noticeService.getNotice(userId, page, num);
        if (!result.isSuccess()) {
            response.addError(result.getErrorCodes());
            return response;
        }
        response.setResult(result.getData());
        return response;
    }

    @RequestMapping(value = "/notice/breed", method = RequestMethod.GET)
    @Auth
    public ApiResponse getBreedNotice(@RequestParam long userId,
                                      @RequestParam(required = false, defaultValue = "1") int page,
                                      @RequestParam(required = false, defaultValue = "20") int num) {
        ApiResponse response = new ApiResponse<>();
        Result<List<TNotice>> result = noticeService.getBreedNotice(userId, page, num);
        if (!result.isSuccess()) {
            response.addError(result.getErrorCodes());
            return response;
        }
        response.setResult(result.getData());
        return response;
    }

    @RequestMapping(value = "/home/info", method = RequestMethod.GET)
    @Auth
    public ApiResponse getBreedNotice(@RequestParam long userId) {
        ApiResponse response = new ApiResponse<>();
        HomeInfoModel infoModel = new HomeInfoModel();
        Result<TAccountTicket> result = accountService.getAccount(userId);
        if (!result.isSuccess()) {
            response.setResult(infoModel);
            return response;
        }
        Result<TAccountIntegral> integralResult = accountService.getAccountIntegral(userId);
        if (!integralResult.isSuccess()) {
            response.setResult(infoModel);
            return response;
        }
        infoModel.setBalance(result.getData().getBalance());
        infoModel.setIntegral(integralResult.getData().getBalance());
        response.setResult(infoModel);
        return response;
    }
}
