package com.hades.farm.enums;

/**
 * Created by zhengzl on 2018/3/10.
 */
public enum AcctOpreType {
    BUY_DUCK(1,"购买种鸭"),
    BUY_EGG(2,"购买种蛋"),
    RECHARGE(3,"充值"),
    WITHDRAW(4,"提现"),
    SELL_DUCK(5,"出售商品鸭"),
    SELL_EGG(6,"出售商品蛋"),
    BUY_FEED(7,"购买饲料"),
    BROKER_CHARGES(8,"佣金"),
    BUY_DOG(9,"购买看门狗"),
    BUY_ROBOT(10,"购买机器人"),
    EXCHANGE_GRADE(11,"兑换积分")
    ;
    private int type;
    private String desc;

    AcctOpreType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
