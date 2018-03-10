package com.hades.farm.core.service;

import com.hades.farm.result.Result;

/**
 * Created by xiaoxu on 2018/3/10.
 */
public interface CodeService {

    /**
     * 获取并发送验证码
     *
     * @param phone        手机号
     * @param randomString 随机字符串
     * @param time         时间戳
     * @param sign         签名
     * @return
     */
    Result<String> getAndSendPhoneCode(String phone, String randomString, long time, String sign);

    /**
     * 获取并发送验证码
     *
     * @param userId
     * @return
     */
    Result<String> getAndSendPhoneCode(long userId);

    /**
     * 验证手机验证码
     *
     * @param phone
     * @param code
     * @return
     */
    Result<Void> validPhoneCode(String phone, String code);

}
