package com.hades.farm.utils;

import org.apache.commons.lang3.math.NumberUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by xiaoxu on 2018/3/12.
 */
public class SystemUtil {

    private static Random R = new Random();
    private static DateFormat DF = new SimpleDateFormat("yyyyMMdd");
    private static DateFormat DF_YY = new SimpleDateFormat("yyMMdd");
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

    /**
     * 订单编号 8位时间＋4位随机数＋用户id后4位
     *
     * @param userId
     * @return
     */
    public static long generateOrderNum(long userId) {
        String dateStr = DF.format(new Date());
        long dateNum = NumberUtils.toLong(dateStr);
        int randNum = getRandomNum();
        long userNum = userId % 10000;
        return dateNum * 100000000L + randNum * 10000 + userNum;
    }

    /**
     * 生成四位随机数 1000~9999
     *
     * @return
     */
    private static int getRandomNum() {
        return R.nextInt(9000) + 1000;
    }


    public static void main(String[] args) {
        System.out.println(getUUID32());
    }


}
