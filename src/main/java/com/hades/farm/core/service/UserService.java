package com.hades.farm.core.service;

import com.hades.farm.api.view.request.RegisterRequest;
import com.hades.farm.core.data.entity.User;
import com.hades.farm.result.Result;

/**
 * Created by xiaoxu on 2018/3/4.
 */
public interface UserService {

    /**
     * 注册
     *
     * @param request
     * @return
     */
    Result<User> userRegister(RegisterRequest request);

    /**
     * 登录
     *
     * @param phone
     * @param pwd
     * @return
     */
    Result<User> login(String phone, String pwd);

    /**
     * 登录
     *
     * @param wechat
     * @return
     */
    Result<User> login(String wechat);

    /**
     * 自动登录
     *
     * @param userId
     * @return
     */
    Result<User> userAutoLogin(long userId);

    /**
     * 获取用户
     *
     * @param userId
     * @return
     */
    Result<User> get(long userId);

}
