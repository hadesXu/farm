package com.hades.farm.core.manager;

import com.hades.farm.core.result.Result;

/**
 * Created by xiaoxu on 2018/3/7.
 */
public interface TokenManager {
    /**
     * 生成token
     *
     * @param userId
     * @param save
     * @return
     */
    Result<String> generateToken(long userId, boolean save);

    /**
     * 删除用户的token
     *
     * @param userId
     * @return
     */
    Result<Void> removeToken(long userId);

    /**
     * 获取用户的token
     *
     * @param userId
     * @return
     */
    Result<String> getToken(long userId);
}
