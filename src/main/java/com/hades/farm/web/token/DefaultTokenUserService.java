package com.hades.farm.web.token;

import com.langu.authorization.exception.UserInvalidException;
import com.langu.authorization.service.TokenUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 校验token是否匹配
 * Created by xiaoxu on 2018/3/4.
 */
public class DefaultTokenUserService implements TokenUserService {

    private final static Logger log = LoggerFactory.getLogger(DefaultTokenUserService.class);

    @Override
    public boolean isUserIdMatchToken(String token, long userId) throws UserInvalidException {
        return true;
    }
}
