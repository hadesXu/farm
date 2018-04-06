package com.hades.farm.api.convert;

import com.hades.farm.api.view.response.UserDetailModel;
import com.hades.farm.api.view.response.UserModel;
import com.hades.farm.core.data.entity.User;

/**
 * Created by xiaoxu on 2018/3/9.
 */
public interface UserConverter {

    /**
     * 转换用户模型
     *
     * @param user
     * @param newToken
     * @return
     */
    UserModel convert(User user, boolean newToken);

    /**
     * 转换用户基本信息模型
     *
     * @param user
     * @return
     */
    UserDetailModel convert(User user);

}
