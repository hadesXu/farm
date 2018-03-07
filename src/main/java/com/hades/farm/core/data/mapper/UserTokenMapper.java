package com.hades.farm.core.data.mapper;

import com.hades.farm.core.data.entity.UserToken;
import org.apache.ibatis.annotations.Param;

/**
 * Created by xiaoxu on 2018/3/7.
 */
public interface UserTokenMapper {

    /**
     * 插入用户token
     *
     * @param token
     * @return
     */
    int insertUserToken(UserToken token);

    /**
     * 根据用户Id获取token
     *
     * @param userId
     * @return
     */
    UserToken getUserTokenById(long userId);

    /**
     * 更新用户token
     *
     * @param userId
     * @param token
     * @return
     */
    int updateUserToken(@Param("userId") long userId, @Param("token") String token);

    /**
     * 删除用户token
     *
     * @param userId
     * @return
     */
    int deleteUserToken(long userId);
}
