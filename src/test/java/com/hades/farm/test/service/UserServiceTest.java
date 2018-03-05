package com.hades.farm.test.service;

import com.hades.farm.core.data.entity.User;
import com.hades.farm.core.service.UserService;
import com.hades.farm.test.BaseTest;
import com.hades.farm.core.result.Result;
import org.junit.Test;

import javax.annotation.Resource;

public class UserServiceTest extends BaseTest {
    @Resource
    private UserService userService;

    @Test
    public void test(){
        Result<User> result= userService.get(1);
        if(result.isSuccess()){
            User obj = result.getObject();
            System.out.println("名称:"+obj.getNick());
        }
    }
}
