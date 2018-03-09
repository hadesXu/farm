package com.hades.farm.core.service;

import com.hades.farm.core.data.entity.User;
import com.hades.farm.result.Result;

/**
 * Created by xiaoxu on 2018/3/4.
 */
public interface UserService {

    /**
     * 注册
     *
     * @param phone
     * @param pwd
     * @param weChat
     * @param fatherNumber
     * @return
     */
    Result<User> userRegister(String phone, String pwd, String weChat, String fatherNumber);

    /**
     * 获取用户
     *
     * @param userId
     * @return
     */
    Result<User> get(long userId);

}
