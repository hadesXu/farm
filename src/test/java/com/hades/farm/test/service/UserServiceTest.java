package com.hades.farm.test.service;

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
}
