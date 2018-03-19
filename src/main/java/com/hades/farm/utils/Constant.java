package com.hades.farm.utils;

import java.math.BigDecimal;

/**
 * Created by xiaoxu on 2018/3/7.
 */
public class Constant {

    public static final long DEFAULT_ID = 0L;//默认ID
    public static final int NUMBER_MINUS_ONE = -1;//数字-1
    public static final int NUMBER_ZERO = 0;//数字0
    public static final int NUMBER_ONE = 1;//数字1


    //菜票对人民币倍数
    public static final int TICKET_CNY = 1;
    //最少购买蛋的数量
    public static final int LEAST_BUY_EGG_NUM = 10;
    //最少购买鸭的数量
    public static final int LEAST_BUY_DUCK_NUM = 10;
    //最少购买饲料数量
    public static final BigDecimal LEAST_BUY_FEED_NUM = new BigDecimal("10");
    //蛋价格（菜票）
    public static final BigDecimal EGG_PRICE = new BigDecimal("2.1");
    //鸭价格
    public static final BigDecimal DUCK_PRICE = new BigDecimal("2.4");
    //饲料价格
    public static final BigDecimal FEED_PRICE = new BigDecimal("0.1");
    //狗的价格
    public static final BigDecimal DOG_PRICE = new BigDecimal("25");
    //机器人价格
    public static final BigDecimal ROBOT_PRICE = new BigDecimal("40");
    //鸭养殖周期
    public static final int BREEDING_DUCK_CYC = 24;
    //蛋养殖周期
    public static final int BREEDING_EGG_CYC = 9;
    //实习期天数
    public static final int PRACTICE_DAY = 3;
    //一只鸭每次喂饲料数量
    public static final BigDecimal DUCK_SINGLE_FEED_AMOUNT = new BigDecimal("1");
    //一个蛋的积分
    public static final BigDecimal EGG_INTEGRAL_RATE = new BigDecimal("0.04");
    //一个鸭的积分
    public static final BigDecimal DUCK_INTEGRAL_RATE = new BigDecimal("0.1");
    //卖出鸭的手续费
    public static final BigDecimal SELL_DUCK_RATE = new BigDecimal("0.05");
    //卖出蛋的手续费
    public static final BigDecimal SELL_EGG_RATE = new BigDecimal("0.05");
    //一只鸭的产蛋量
    public static final int PRODUCE_EGGS = 3;


    public static final String DEFAULT_CODE = "0000";//默认code
    public static final String CODE_MESSAGE_TEMPLATE = "欢迎使用本产品， 您本次操作操作验证码为：%s, 3分钟有效~";
    //师爷条件人数
    public static final int ADVISER_CONDITION_NUMBER = 200;
    //师爷条件代数
    public static final int ADVISER_CONDITION_GENERATION = 3;

    //地主条件忍住
    public static final int LANDLORD_CONDITION_NUMBER = 10;
    public static final int LANDLORD_CONDITION_GENERATION = 1;
}
