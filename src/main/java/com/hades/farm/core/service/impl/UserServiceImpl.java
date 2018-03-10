package com.hades.farm.core.service.impl;

import com.hades.farm.api.view.request.RegisterRequest;
import com.hades.farm.core.data.entity.User;
import com.hades.farm.core.data.mapper.UserMapper;
import com.hades.farm.core.service.CodeService;
import com.hades.farm.core.service.UserService;
import com.hades.farm.enums.Grade;
import com.hades.farm.result.ErrorCode;
import com.hades.farm.result.Result;
import com.hades.farm.utils.AccountValidatorUtil;
import com.hades.farm.utils.Constant;
import com.sun.org.apache.bcel.internal.classfile.Code;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by xiaoxu on 2018/3/4.
 */
@Service
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private UserMapper userMapper;
    @Resource
    private CodeService codeService;

    @Override
    public Result<User> userRegister(RegisterRequest request) {
        Result<User> result = Result.newResult();
        Result<Void> voidResult = checkBeforeRegister(request);
        if (!voidResult.isSuccess()) {
            result.addErrors(voidResult.getErrorCodes());
            return result;
        }
        User user = generateUser(request);
        int uRes = userMapper.insert(user);
        if (uRes == Constant.NUMBER_ONE) {
            result.addError(ErrorCode.ADD_ERR);
            return result;
        }
        result.setData(user);
        return result;
    }

    @Override
    public Result<User> login(String phone, String pwd) {
        Result<User> result = Result.newResult();
        User user = userMapper.getUserPhone(phone);
        if (user == null) {
            result.addError(ErrorCode.USER_NOT_EXIST);
            return result;
        }
        if (!StringUtils.equals(user.getPassword(), DigestUtils.md5Hex(pwd))) {
            result.addError(ErrorCode.PASSWORD_INVALID);
            return result;
        }
        result.setData(user);
        return result;
    }

    @Override
    public Result<User> login(String wechat) {
        Result<User> result = Result.newResult();
        User user = userMapper.getUserByWeChat(wechat);
        if (user == null) {
            result.addError(ErrorCode.USER_NOT_EXIST);
            return result;
        }
        return result;
    }

    @Override
    public Result<User> userAutoLogin(long userId) {
        Result<User> result = Result.newResult();
        User user = userMapper.getUserById(userId);
        if (user == null) {
            result.addError(ErrorCode.USER_NOT_EXIST);
            return result;
        }
        return result;
    }

    @Override
    public Result<User> get(long userId) {
        Result<User> result = Result.newResult();
        User user = userMapper.getUserById(userId);
        if (user != null) {
            result.setData(user);
        }
        return result;
    }

    /**
     * 注册前检查
     *
     * @param request
     * @return
     */
    private Result<Void> checkBeforeRegister(RegisterRequest request) {
        Result<Void> result = Result.newResult();
        if (AccountValidatorUtil.isMobile(request.getPhone())) {
            result.addError(ErrorCode.PHONE_FORMAT_ERROR);
            return result;
        }
        if (AccountValidatorUtil.isPassword(request.getPwd())) {
            result.addError(ErrorCode.PWD_FORMAT_ERROR);
            return result;
        }
        User user = userMapper.getUserByWeChat(request.getPhone());
        if (user != null) {
            result.addError(ErrorCode.PHONE_EXIST);
            return result;
        }
        user = userMapper.getUserByWeChat(request.getWechat());
        if (user != null) {
            result.addError(ErrorCode.WECHAT_EXIST);
            return result;
        }
        Result<Void> voidResult = codeService.validPhoneCode(request.getPhone(), request.getCode());
        if (!voidResult.isSuccess()) {
            return voidResult;
        }
        return result;
    }

    private User generateUser(RegisterRequest request) {
        User user = new User();
        user.setPassword(DigestUtils.md5Hex(request.getPwd()));
        user.setImgUrl(request.getFace());
        user.setGrade(Grade.APPRENTICE.getType());
        user.setAddTime(new Date());
        user.setFatherNumber(request.getFatherNumber());
        user.setWechat(request.getWechat());
        user.setTelephone(request.getPhone());
        return user;
    }
}
