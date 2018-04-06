package com.hades.farm.utils;

import java.util.UUID;

/**
 * Created by xiaoxu on 2018/3/12.
 */
public class SystemUtil {
    /**
     * UUID 生成
     *
     * @return
     */
    public static String getUUID32() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    public static boolean isNull(String str) {
        return str == null || str.equals("null") ? true : false;
    }

    /**
     * 默认年龄时间戳
     *
     * @return
     */
    public static long defaultBirth() {
        return System.currentTimeMillis() - DateUtils.YEAR * Constant.DEFAULT_AGE;
    }

    /**
     * 手机号
     *
     * @param phone
     * @return
     */
    public static String replacePhone(String phone) {
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    public static void main(String[] args) {
        System.out.println(getUUID32());
    }


}
