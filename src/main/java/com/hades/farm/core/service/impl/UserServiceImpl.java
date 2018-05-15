package com.hades.farm.core.service.impl;

import com.hades.farm.api.view.request.RegisterRequest;
import com.hades.farm.api.view.request.UpdateUserRequest;
import com.hades.farm.core.data.entity.TAccountIntegral;
import com.hades.farm.core.data.entity.TAccountTicket;
import com.hades.farm.core.data.entity.TIdentityCardRecord;
import com.hades.farm.core.data.entity.User;
import com.hades.farm.core.data.mapper.TAccountIntegralMapper;
import com.hades.farm.core.data.mapper.TAccountTicketMapper;
import com.hades.farm.core.data.mapper.TIdentityCardRecordMapper;
import com.hades.farm.core.data.mapper.UserMapper;
import com.hades.farm.core.service.CodeService;
import com.hades.farm.core.service.UserService;
import com.hades.farm.enums.Grade;
import com.hades.farm.enums.Sex;
import com.hades.farm.result.ErrorCode;
import com.hades.farm.result.Result;
import com.hades.farm.utils.AccountValidatorUtil;
import com.hades.farm.utils.Constant;
import com.hades.farm.utils.NickUtil;
import com.hades.farm.utils.SystemUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaoxu on 2018/3/4.
 */
@Service
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private UserMapper userMapper;
    @Resource
    private TAccountIntegralMapper tAccountIntegralMapper;
    @Resource
    private TAccountTicketMapper tAccountTicketMapper;
    @Resource
    private TIdentityCardRecordMapper tIdentityCardRecordMapper;
    @Resource
    private CodeService codeService;

    @Autowired
    private EggWareHouseServiceImpl eggWareHouseService;

    @Autowired
    private DuckWareHouseServiceImpl duckWareHouseService;

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
        if (uRes != Constant.NUMBER_ONE) {
            result.addError(ErrorCode.ADD_ERR);
            return result;
        }
        //初始化账户
        tAccountTicketMapper.insert(new TAccountTicket(user.getId(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, new Date()));
        //初始化积分账户
        tAccountIntegralMapper.insert(new TAccountIntegral(user.getId(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, new Date()));

        try{
            //初始化鸭仓
            duckWareHouseService.addWareHouse(user.getId());
            //初始化蛋仓
            eggWareHouseService.addWareHouse(user.getId());
        } catch (Exception e){
            e.printStackTrace();
        }

        if(user.getParentId() != null) {
            Map map = new HashMap();
            map.put("user_id",user.getId());
            map.put("if_back","1");
            map.put("add_time",new Date());
            userMapper.insertRegister(map);
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
    public Result<User> login(String wechat, String name, String imgUrl) {
        Result<User> result = Result.newResult();
        User user = userMapper.getUserByWeChat(wechat);
        if (user == null) {
            result.addError(ErrorCode.USER_NOT_EXIST);
            return result;
        }
        userMapper.updateNameAndImgUrl(user.getId(), name, imgUrl);
        user.setImgUrl(imgUrl);
        user.setName(name);
        result.setData(user);
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

    @Override
    public Result<Void> updatePwd(String phone, String code, String pwd) {
        Result<Void> result = Result.newResult();
        if (!AccountValidatorUtil.isPassword(pwd)) {
            result.addError(ErrorCode.PWD_FORMAT_ERROR);
            return result;
        }
        Result<Void> voidResult = codeService.validPhoneCode(phone, code);
        if (!voidResult.isSuccess()) {
            return voidResult;
        }
        User user = userMapper.getUserPhone(phone);
        if (user == null) {
            result.addError(ErrorCode.USER_NOT_EXIST);
            return result;
        }
        int uRes = userMapper.updatePwd(user.getId(), DigestUtils.md5Hex(pwd));
        if (uRes != Constant.NUMBER_ONE) {
            result.addError(ErrorCode.UPDATE_ERR);
            return result;
        }
        return result;
    }

    @Override
    public Result<Void> updatePwd(long userId, String oldPwd, String pwd) {
        Result<Void> result = Result.newResult();
        if (!AccountValidatorUtil.isPassword(pwd)) {
            result.addError(ErrorCode.PWD_FORMAT_ERROR);
            return result;
        }
        User user = userMapper.getUserById(userId);
        if (user == null) {
            result.addError(ErrorCode.USER_NOT_EXIST);
            return result;
        }
        if (!StringUtils.equals(user.getPassword(), DigestUtils.md5Hex(oldPwd))) {
            result.addError(ErrorCode.PASSWORD_INVALID);
            return result;
        }
        int uRes = userMapper.updatePwd(userId, DigestUtils.md5Hex(pwd));
        if (uRes != Constant.NUMBER_ONE) {
            result.addError(ErrorCode.UPDATE_ERR);
            return result;
        }
        return result;
    }

    @Override
    public Result<Void> updateUser(UpdateUserRequest request) {
        Result<Void> result = Result.newResult();
        User user = userMapper.getUserById(request.getUserId());
        if (user == null) {
            result.addError(ErrorCode.USER_NOT_EXIST);
            return result;
        }
        Integer auth = null;
        if (user.getIsAuth() != Constant.NUMBER_TWO) {
            if (!(SystemUtil.nameValidate(request.getRealName()) && SystemUtil.IDCardValidate(request.getIdNo()))) {
                result.addError(ErrorCode.AUTH_USER_INVALID);
                return result;
            }
            TIdentityCardRecord record = tIdentityCardRecordMapper.getByIdNo(request.getIdNo());
            if (record != null) {
                result.addError(ErrorCode.AUTH_USER_EXIST);
                return result;
            }
            record = new TIdentityCardRecord();
            record.setRealName(request.getRealName());
            record.setIdNo(request.getIdNo());
            record.setUserId(request.getUserId());
            int uRes = tIdentityCardRecordMapper.insert(record);
            if (uRes != Constant.NUMBER_ONE) {
                result.addError(ErrorCode.ADD_ERR);
                return result;
            }
            auth = Constant.NUMBER_TWO;
        }
        int uRes = userMapper.updateUser(request.getUserId(), request.getBirth(), Sex.getType(request.getSex()).type, auth);
        if (uRes != Constant.NUMBER_ONE) {
            result.addError(ErrorCode.UPDATE_ERR);
            return result;
        }
        return result;
    }

    @Override
    public Result<List<User>> getApprentice(long userId, int page, int num) {
        Result<List<User>> result = Result.newResult();
        int offset = (page - 1) * num;
        List<User> userList = userMapper.getApprenticeOffset(userId, offset, num);
        if (CollectionUtils.isNotEmpty(userList)) {
            result.setData(userList);
        }
        return result;
    }

    @Override
    public Result<List<User>> getSon(long userId, int page, int num) {
        Result<List<User>> result = Result.newResult();
        int offset = (page - 1) * num;
        List<User> userList = userMapper.getSon(userId, offset, num);
        if (CollectionUtils.isNotEmpty(userList)) {
            result.setData(userList);
        }
        return result;
    }

    @Override
    public Result<List<User>> getDisciple(long userId, int page, int num) {
        Result<List<User>> result = Result.newResult();
        int offset = (page - 1) * num;
        List<User> userList = userMapper.getDisciple(userId, offset, num);
        if (CollectionUtils.isNotEmpty(userList)) {
            result.setData(userList);
        }
        return result;
    }

    @Override
    public int getApprenticeCount(long userId) {
        return userMapper.getApprenticeCount(userId);
    }

    @Override
    public int getSonCount(long userId) {
        return userMapper.getSonCount(userId);
    }

    @Override
    public int getDiscipleCount(long userId) {
        return userMapper.getDiscipleCount(userId);
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
        if (!AccountValidatorUtil.isPassword2(request.getPwd())) {
            result.addError(ErrorCode.PWD_FORMAT_ERROR);
            return result;
        }
        User user = userMapper.getUserPhone(request.getPhone());
        if (user != null) {
            result.addError(ErrorCode.PHONE_EXIST);
            return result;
        }
        if (!SystemUtil.isNull(request.getWechat()) && !"undefined".equals(request.getWechat())) {
            user = userMapper.getUserByWeChat(request.getWechat());
            if (user != null) {
                result.addError(ErrorCode.WECHAT_EXIST);
                return result;
            }
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
        user.setGrade(Grade.APPRENTICE.getType());
        user.setAddTime(new Date());
        user.setSex(Sex.UNDEFINED.type);
        user.setBirth(SystemUtil.defaultBirth());
        user.setsEgg(Constant.NUMBER_ONE);
        user.setsDuck(Constant.NUMBER_ONE);
        user.setActive(Constant.NUMBER_TWO);
        user.setIsGroup(Constant.NUMBER_ONE);
        user.setIsTrainee(Constant.NUMBER_TWO);
        String parents = "";
        if (request.getParentId() != null) {
            User parentUser = userMapper.getUserById(request.getParentId());
            if (parentUser != null) {
                user.setParentId(request.getParentId());
                user.setGeneration(parentUser.getGeneration() + Constant.NUMBER_ONE);
                if (parentUser.getIsGroup() == Constant.NUMBER_TWO) {
                    user.setIsGroup(parentUser.getIsGroup());
                    user.setGroupBossId(parentUser.getGroupBossId());
                }
                parents += parentUser.getId(); //上一代
                if (parentUser.getParentId() != null  ) {
                    User pparentUser = userMapper.getUserById(parentUser.getParentId());   //爷信息
                    if(pparentUser != null) {
                        parents +=","+parentUser.getParentId(); //上两代

                        if(pparentUser.getParentId() != null) {
                            User ppparentUser = userMapper.getUserById(pparentUser.getParentId());   //祖信息
                            if(ppparentUser != null) {
                                parents +=","+pparentUser.getParentId(); //上三代
                            }
                        }
                    }
                }
            }
        }
         user.setParents(parents);

        if (!SystemUtil.isNull(request.getWechat()) && !"undefined".equals(request.getWechat())) {
            user.setWechat(request.getWechat());
        }
        user.setTelephone(request.getPhone());
        if (!SystemUtil.isNull(request.getName()) && !"undefined".equals(request.getName())) {
            user.setName(request.getName());
        } else {
            user.setName(NickUtil.randomNick());
        }
        if (!SystemUtil.isNull(request.getImgUrl())) {
            user.setImgUrl(request.getImgUrl());
        }
        return user;
    }
}
