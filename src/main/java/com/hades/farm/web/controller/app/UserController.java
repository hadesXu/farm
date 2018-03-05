package com.hades.farm.web.controller.app;

import com.hades.farm.core.data.entity.User;
import com.hades.farm.core.result.Result;
import com.hades.farm.core.service.UserService;
import com.hades.farm.web.model.ViewResult;
import com.hades.farm.web.model.view.user.UserModel;
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
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/user/getUserById", method = RequestMethod.POST)
//    @Auth
    public ViewResult getUserById(@RequestParam long userId) {
        ViewResult<UserModel> result = new ViewResult<>();
        Result<User> uRes = userService.get(userId);
        if (!uRes.isSuccess()) {
            result.addError(uRes.getErrMsg());
            return result;
        }
        return result;
    }
}
