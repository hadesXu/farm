package com.hades.farm.core.data.mapper;


import com.hades.farm.core.data.entity.User;

/**
 * Created by xiaoxu on 2018/3/4.
 */
public interface UserMapper {

    /**
     * 根据ID获取用户
     *
     * @param userId
     * @return
     */
    User getUserById(long userId);


}
