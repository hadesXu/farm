package com.hades.farm.core.manager.impl;

import com.hades.farm.core.data.entity.UserToken;
import com.hades.farm.core.manager.TokenManager;
import com.hades.farm.core.data.mapper.UserTokenMapper;
import com.hades.farm.result.ErrorCode;
import com.hades.farm.result.Result;
import com.langu.authorization.exception.TokenGenerateException;
import com.langu.authorization.token.BlowfishToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by xiaoxu on 2018/3/7.
 */
@Component
public class TokenManagerImpl implements TokenManager {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final static String DEFAULT_CLIENT = "app";

    @Resource
    private UserTokenMapper userTokenMapper;

    @Override
    public Result<String> generateToken(long userId, boolean save) {
        Result<String> result = new Result<>();
        BlowfishToken token = new BlowfishToken();
        token.setUserId(userId);
        token.setClient(DEFAULT_CLIENT);
        token.setTokenTime(System.currentTimeMillis());
        try {
            String str = token.generate();
            if (save) {
                UserToken userToken = userTokenMapper.getUserTokenById(userId);
                if (userToken == null) {
                    userToken = new UserToken();
                    userToken.setUserId(userId);
                    userToken.setToken(str);
                    userToken.setUpdateTime(new Date());
                    userToken.setToken(str);
                    userTokenMapper.insertUserToken(userToken);
                } else {
                    userTokenMapper.updateUserToken(userId, str);
                }
            }
            result.setData(str);
        } catch (TokenGenerateException e) {
            result.addError(ErrorCode.SYSTEM_ERROR);
            logger.error("GenerateToken exception, userId:{}", userId, e);
        }
        return result;
    }

    @Override
    public Result<Void> removeToken(long userId) {
        Result<Void> result = new Result<>();
        userTokenMapper.deleteUserToken(userId);
        return result;
    }

    @Override
    public Result<String> getToken(long userId) {
        Result<String> result = new Result<>();
        UserToken userToken = userTokenMapper.getUserTokenById(userId);
        if (userToken != null) {
            result.setData(userToken.getToken());
        }
        return result;
    }
}
