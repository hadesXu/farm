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
    EXCHANGE_GRADE(11,"兑换菜币"),
    PAY_STEAL_EGG(19,"支付偷蛋费用"),
    PAY_STEAL_DUCK(20,"支付偷鸭费用"),
    SELL_EGG_FEE(21,"出售蛋手续费"),
    SELL_DUCK_FEE(22,"出售鸭手续费"),
    SELL_EGG_INTEGRAL_FEE(23,"出售蛋菜币费"),
    SELL_DUCK_INTEGRAL_FEE(24,"出售鸭菜币费"),
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
