package com.hades.farm.core.service.impl;

import com.hades.farm.core.data.entity.TSmsRecord;
import com.hades.farm.core.data.entity.User;
import com.hades.farm.core.data.mapper.TSmsRecordMapper;
import com.hades.farm.core.data.mapper.UserMapper;
import com.hades.farm.core.service.CodeService;
import com.hades.farm.result.ErrorCode;
import com.hades.farm.result.Result;
import com.hades.farm.utils.Constant;
import com.hades.farm.utils.SmsUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.time.Instant;
import java.time.format.TextStyle;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Created by xiaoxu on 2018/3/10.
 */
@Service
public class CodeServiceImpl implements CodeService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${switch.debug}")
    private boolean envDev;
    @Resource
    private UserMapper userMapper;
    @Resource
    private TSmsRecordMapper tSmsRecordMapper;

    @Override
    public Result<String> getAndSendPhoneCode(String phone, String weChat, String ip) {
        Result<String> result = new Result<>();
        if (StringUtils.isBlank(weChat) || StringUtils.isBlank(phone)) {
            result.addError(ErrorCode.INVALID_REQUEST);
            return result;
        }
        User user = userMapper.getUserPhone(phone);
        if (user != null) {
            result.addError(ErrorCode.PHONE_EXIST);
            return result;
        }
        user = userMapper.getUserPhone(weChat);
        if (user != null) {
            result.addError(ErrorCode.WECHAT_EXIST);
            return result;
        }
        return sendCode(phone, ip);
    }

    @Override
    public Result<String> getAndSendPhoneCode(String phone, String ip) {
        Result<String> result = Result.newResult();
        if (StringUtils.isBlank(phone)) {
            result.addError(ErrorCode.INVALID_REQUEST);
            return result;
        }
        User user = userMapper.getUserPhone(phone);
        if (user == null) {
            result.addError(ErrorCode.USER_NOT_EXIST);
            return result;
        }
        return sendCode(phone, ip);
    }

    @Override
    public Result<String> getAndSendPhoneCode(long userId) {
        Result<String> result = Result.newResult();
        User user = userMapper.getUserById(userId);
        if (user == null) {
            result.addError(ErrorCode.SYSTEM_ERROR);
            return result;
        }
        return sendCode(user.getTelephone(), null);
    }

    @Override
    public Result<Void> validPhoneCode(String phone, String code) {
        Result<Void> result = new Result<>();
        if (isBlank(phone) || isBlank(code)) {
            result.addError(ErrorCode.ARGUMENTS_NULL);
            return result;
        }
        if (envDev && code.equals(Constant.DEFAULT_CODE)) {
            return result;
        }
        String dbCode = getDbCode(phone);
        if (isBlank(dbCode)) {
            result.addError(ErrorCode.PHONE_CODE_INVALID);
            return result;
        }
        if (!StringUtils.equals(dbCode, code)) {
            result.addError(ErrorCode.PHONE_CODE_INVALID);
        }
        return result;
    }

    private boolean checkSign(String phone, String randStr, long time, String sign) {
        if (isBlank(sign)) {
            return false;
        }
        String plainText = "time=" + phone + "*phone=" + randStr + "&randStr=" + time;
        String encryText = DigestUtils.md5Hex(plainText);

        return StringUtils.equals(StringUtils.upperCase(encryText), sign);
    }

    private Result<String> sendCode(String phone, String ip) {
        Result<String> result = Result.newResult();
        long now = Instant.now().toEpochMilli();
        String code = null;
        TSmsRecord tSmsRecord = tSmsRecordMapper.getByPhone(phone);
        boolean hasUpdate = false;
        if (tSmsRecord == null) {
            tSmsRecord = new TSmsRecord();
            tSmsRecord.setPhone(phone);
            tSmsRecord.setCreateTime(now);
            tSmsRecord.setSendCount(Constant.NUMBER_ONE);
            tSmsRecord.setSendIp(ip);
            code = RandomStringUtils.randomNumeric(4);
        } else {
            tSmsRecord.setSendCount(tSmsRecord.getSendCount() + Constant.NUMBER_ONE);
            if ((now - tSmsRecord.getLastTime()) < DateUtils.MILLIS_PER_MINUTE) {
                result.addError(ErrorCode.PHONE_CODE_REQUEST_FREQ);
                return result;
            } else if ((now - tSmsRecord.getLastTime()) > 3 * DateUtils.MILLIS_PER_MINUTE) {
                code = RandomStringUtils.randomNumeric(4);
            } else {
                code = tSmsRecord.getCode();
            }
            hasUpdate = true;
        }
        Result<Void> sendRes = SmsUtil.sendSms(phone, String.format(Constant.CODE_MESSAGE_TEMPLATE, code));
        if (!sendRes.isSuccess()) {
            logger.error("Send code message failed, phone:{}", phone);
        } else {
            tSmsRecord.setCode(code);
            tSmsRecord.setLastTime(now);
            if (hasUpdate) {
                tSmsRecordMapper.updateByPrimaryKey(tSmsRecord);
            } else {
                tSmsRecordMapper.insert(tSmsRecord);
            }
        }
        return result;
    }

    /**
     * 获取数据库code
     *
     * @param phone
     * @return
     */
    private String getDbCode(String phone) {
        TSmsRecord tSmsRecord = tSmsRecordMapper.getByPhone(phone);
        if (tSmsRecord == null || (Instant.now().toEpochMilli() - tSmsRecord.getLastTime()) > 3 * DateUtils.MILLIS_PER_MINUTE) {
            return null;
        } else {
            return tSmsRecord.getCode();
        }
    }

}
