package com.hades.farm.api.controller;

import com.hades.farm.api.convert.UserConverter;
import com.hades.farm.api.view.ApiResponse;
import com.hades.farm.api.view.request.RegisterRequest;
import com.hades.farm.api.view.request.UpdateUserRequest;
import com.hades.farm.api.view.response.ApprenticeModel;
import com.hades.farm.api.view.response.HomeInfoModel;
import com.hades.farm.api.view.response.UserDetailModel;
import com.hades.farm.core.data.entity.TAccountIntegral;
import com.hades.farm.core.data.entity.TAccountTicket;
import com.hades.farm.core.data.entity.TNotice;
import com.hades.farm.core.data.entity.User;
import com.hades.farm.core.service.AccountService;
import com.hades.farm.core.service.EggBreedingService;
import com.hades.farm.core.service.NoticeService;
import com.hades.farm.core.service.UserService;
import com.hades.farm.result.Result;
import com.hades.farm.api.view.response.UserModel;
import com.hades.farm.utils.Constant;
import com.langu.authorization.annotation.Auth;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
    @Resource
    private EggBreedingService eggBreedingService;

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
    public ApiResponse updatePwd(@RequestParam(required = false, defaultValue = "") String phone,
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

    @RequestMapping(value = "/update/pwd2", method = RequestMethod.POST)
    @Auth
    public ApiResponse duckBreeding(@RequestParam long userId, @RequestParam String oldPwd, @RequestParam String pwd) {
        ApiResponse response = new ApiResponse<>();
        Result<Void> registerRes = userService.updatePwd(userId, oldPwd, pwd);
        if (!registerRes.isSuccess()) {
            response.addError(registerRes.getErrorCodes());
            return response;
        }
        return response;
    }

    @RequestMapping(value = "/update/user", method = RequestMethod.POST)
    @Auth
    public ApiResponse duckBreeding(UpdateUserRequest request) {
        ApiResponse response = new ApiResponse<>();
        Result<Void> registerRes = userService.updateUser(request);
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
//        Result<List<TNotice>> result = noticeService.getBreedNotice(userId, page, num);
//        if (!result.isSuccess()) {
//            response.addError(result.getErrorCodes());
//            return response;
//        }
//        response.setResult(result.getData());
        List<Map> list = eggBreedingService.queryBreeList(userId, (page-1)*num, num);

        if(list != null && list.size() > 0) {
            for(Map map : list) {
                 map.put("add_time_f",map.get("add_time").toString().substring(0,10));
                 String info = "";
                 int num_harvest = (int) map.get("num_harvest");
                 if("1".equals(map.get("ty"))) {  //蛋
                      info += map.get("num")+"只蛋,孵化第"+map.get("day")+"天";
                      if(num_harvest > 0) {
                          info += "已孵化"+num_harvest+"只鸭";
                      }
                 }else if("2".equals(map.get("ty"))) { //鸭
                     info += map.get("num")+"只鸭,养殖第"+map.get("day")+"天";
                     if(num_harvest > 0) {
                         info += "已生产"+num_harvest+"只蛋";
                     }
                 }
                 map.put("info",info);
            }
        }
        response.setResult(list);

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
        infoModel.setFrozen(result.getData().getFrozen());
        infoModel.setIntegral(integralResult.getData().getBalance());
        response.setResult(infoModel);
        return response;
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ApiResponse<UserDetailModel> getDetail(@RequestParam(required = false, defaultValue = "0") long userId) {
        ApiResponse<UserDetailModel> response = new ApiResponse<>();
        Result<User> loginRes = userService.get(userId);
        if (!loginRes.isSuccess()) {
            response.addError(loginRes.getErrorCodes());
            return response;
        }
        response.setResult(userConverter.convert(loginRes.getData()));
        return response;
    }


    @RequestMapping(value = "/apprentice", method = RequestMethod.GET)
    @Auth
    public ApiResponse getApprentice(@RequestParam long userId,
                                     @RequestParam(required = false, defaultValue = "1") int page,
                                     @RequestParam(required = false, defaultValue = "20") int num,
                                     @RequestParam(required = false, defaultValue = "0") int type) {
        ApiResponse response = new ApiResponse<>();
        Result<List<User>> result = null;
        if (type == Constant.NUMBER_ZERO) {
            result = userService.getApprentice(userId, page, num);
        } else if (type == Constant.NUMBER_ONE) {
            result = userService.getSon(userId, page, num);
        } else {
            result = userService.getDisciple(userId, page, num);
        }
        if (!result.isSuccess()) {
            response.addError(result.getErrorCodes());
            return response;
        }
        response.setResult(result.getData());
        return response;
    }

    @RequestMapping(value = "/apprentice/count", method = RequestMethod.GET)
    @Auth
    public ApiResponse getApprentice(@RequestParam long userId) {
        ApiResponse response = new ApiResponse<>();
        ApprenticeModel model = new ApprenticeModel();
        model.setCount(userService.getApprenticeCount(userId));
        model.setSonCount(userService.getSonCount(userId));
        model.setDiscipleCount(userService.getDiscipleCount(userId));
        response.setResult(model);
        return response;
    }


}
