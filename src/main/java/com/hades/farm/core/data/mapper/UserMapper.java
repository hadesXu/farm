package com.hades.farm.core.data.mapper;


import com.hades.farm.core.data.entity.User;

/**
 * Created by xiaoxu on 2018/3/4.
 */
public interface UserMapper {

    int insert(User user);

    User getUserById(long userId);

    User getUserByWeChat(String weChat);

    User getUserPhone(String telephone);


}
