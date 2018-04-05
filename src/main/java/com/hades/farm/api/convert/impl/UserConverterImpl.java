package com.hades.farm.api.convert.impl;

import com.hades.farm.api.convert.UserConverter;
import com.hades.farm.api.view.response.UserModel;
import com.hades.farm.core.data.entity.User;
import com.hades.farm.core.manager.TokenManager;
import com.hades.farm.result.Result;
import com.hades.farm.web.config.WeChatConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by xiaoxu on 2018/3/9.
 */
@Component
public class UserConverterImpl implements UserConverter {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private TokenManager tokenManager;
    @Autowired
    private WeChatConfig weChatConfig;

    @Override
    public UserModel convert(User user, boolean newToken) {
        UserModel userModel = new UserModel();
        userModel.setUserId(user.getId());
        userModel.setPhone(user.getTelephone());
        userModel.setFace(user.getImgUrl());
        userModel.setNick(user.getName());
        userModel.setParentId(user.getParentId());
        userModel.setShareUrl(weChatConfig.getServerUrl() + "index.html?source=" + user.getId());
        if (newToken) {
            Result<String> tokenRes = tokenManager.generateToken(user.getId(), true);
            if (tokenRes.isSuccess()) {
                try {
                    userModel.setToken(URLEncoder.encode(tokenRes.getData(), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    logger.error("Encode token failed, userId:{}, token:{}", user.getId(), tokenRes.getData());
                }
            } else {
                logger.error("GenerateToken failed, userId:{}", user.getId());
            }
        }
        return userModel;
    }
}
