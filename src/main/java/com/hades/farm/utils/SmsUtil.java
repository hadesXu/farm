package com.hades.farm.utils;

import com.hades.farm.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xiaoxu on 2018/3/10.
 */
public class SmsUtil {
    private final static Logger logger = LoggerFactory.getLogger(SmsUtil.class);

    public static Result<Void> sendSms(String phone, String msg) {
        logger.info("phone:{} msg:{}", phone, msg);
        Result<Void> result = new Result<>();
        return result;
    }

}
