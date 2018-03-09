package com.hades.farm.web.token;

import com.hades.farm.core.manager.TokenManager;
import com.hades.farm.core.result.Result;
import com.langu.authorization.exception.UserInvalidException;
import com.langu.authorization.service.TokenUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * 校验token是否匹配
 * Created by xiaoxu on 2018/3/4.
 */
public class DefaultTokenUserService implements TokenUserService {

    private final static Logger log = LoggerFactory.getLogger(DefaultTokenUserService.class);

    @Resource
    private TokenManager tokenManager;

    @Override
    public boolean isUserIdMatchToken(String token, long userId) throws UserInvalidException {
        Result<String> checkRes = tokenManager.getToken(userId);
        if (checkRes.isSuccess()) {
            return StringUtils.equals(token, checkRes.getObject());
        } else {
            log.error("getUserToken failed. userId:{} token:{}", userId, token);
        }
        return false;
    }
}
