package com.hades.farm.test.service;

import com.hades.farm.api.view.request.RegisterRequest;
import com.hades.farm.core.data.entity.User;
import com.hades.farm.core.service.UserService;
import com.hades.farm.result.Result;
import com.hades.farm.test.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

public class UserServiceTest extends BaseTest {
    @Resource
    private UserService userService;

    @Test
    public void test() {
        Result<User> result = userService.get(1);
        if (result.isSuccess()) {
            User obj = result.getData();
            System.out.println("名称:" + obj.getName());
        }
    }


    @Test
    public void userRegister() {
        RegisterRequest request = new RegisterRequest();
        request.setPhone("13612831910");
        request.setWechat("ASDFGHKKAUDSUAS");
        request.setPwd("Lq123456");
        request.setCode("0000");
        Result<User> result = userService.userRegister(request);
        if (result.isSuccess()) {
            User obj = result.getData();
            System.out.println("名称:" + obj.getName());
        }
    }

}
