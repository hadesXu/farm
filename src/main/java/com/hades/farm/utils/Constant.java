package com.hades.farm.utils;

import java.math.BigDecimal;

/**
 * Created by xiaoxu on 2018/3/7.
 */
public class Constant {
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
    //鸭养殖周期
    public static final int BREEDING_DUCK_CYC = 24;
    //蛋养殖周期
    public static final int BREEDING_EGG_CYC =  9;


}
