package com.hades.farm.core.service;

import com.hades.farm.api.view.request.RegisterRequest;
import com.hades.farm.api.view.request.UpdateUserRequest;
import com.hades.farm.core.data.entity.User;
import com.hades.farm.result.Result;

import java.util.List;

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
    Result<User> login(String wechat, String name, String imgUrl);

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

    /**
     * 更新密码
     *
     * @param phone
     * @param code
     * @param pwd
     * @return
     */
    Result<Void> updatePwd(String phone, String code, String pwd);

    /**
     * 更新密码
     *
     * @param oldPwd
     * @param pwd
     * @return
     */
    Result<Void> updatePwd(long userId, String oldPwd, String pwd);

    /**
     * 更新用户
     *
     * @param request
     * @return
     */
    Result<Void> updateUser(UpdateUserRequest request);


    /**
     * 获取自己的徒弟
     *
     * @param userId
     * @param page
     * @param num
     * @return
     */
    Result<List<User>> getApprentice(long userId, int page, int num);

    /**
     * 获取徒弟数量
     *
     * @param userId
     * @return
     */
    int getApprenticeCount(long userId);

    /**
     * 获取徒子
     *
     * @param userId
     * @param page
     * @param num
     * @return
     */
    Result<List<User>> getSon(long userId, int page, int num);

    /**
     * 获取徒子数量
     *
     * @param userId
     * @return
     */
    int getSonCount(long userId);

    /**
     * 获取徒孙
     *
     * @param userId
     * @param page
     * @param num
     * @return
     */
    Result<List<User>> getDisciple(long userId, int page, int num);

    /**
     * 获取徒孙数量
     *
     * @param userId
     * @return
     */
    int getDiscipleCount(long userId);


}
