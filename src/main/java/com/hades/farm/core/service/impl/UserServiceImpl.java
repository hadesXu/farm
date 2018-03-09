package com.hades.farm.core.service.impl;

import com.hades.farm.core.data.entity.User;
import com.hades.farm.core.data.mapper.UserMapper;
import com.hades.farm.core.service.UserService;
import com.hades.farm.result.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by xiaoxu on 2018/3/4.
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public Result<User> userRegister(String phone, String pwd, String weChat, String fatherNumber) {
        return null;
    }

    @Override
    public Result<User> get(long userId) {
        Result<User> result = Result.newResult();
        User user = userMapper.getUserById(userId);
        if (user != null) {
            result.setData(user);
        }
        return result;
    }
}
