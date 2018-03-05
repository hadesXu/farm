package com.hades.farm.core.service;

import com.hades.farm.core.data.entity.User;
import com.hades.farm.core.result.Result;

/**
 * Created by xiaoxu on 2018/3/4.
 */
public interface UserService {

    /**
     * 获取用户
     *
     * @param userId
     * @return
     */
    Result<User> get(long userId);

}
