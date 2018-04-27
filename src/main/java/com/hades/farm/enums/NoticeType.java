package com.hades.farm.enums;

/**
 * Created by zhengzl on 2018/3/10.
 */
public enum NoticeType {
    BUY_DUCK(1, "购买种鸭", "成功购买num个鸭"),
    BUY_EGG(2, "购买种蛋", "成功购买num个鸭蛋"),
    RECHARGE(3, "充值", "充值amount"),
    WITHDRAW(4, "提现", "提现amount"),
    SELL_DUCK(5, "出售商品鸭", "成功挂单出售num个鸭"),
    SELL_EGG(6, "出售商品蛋", "成功出售num个鸭蛋"),
    BUY_FEED(7, "买饲料", "成功购买num包饲料"),
    BROKER_CHARGES(8, "佣金", "获得佣金amount"),
    BUY_DOG(9, "购买看门狗", "成功购买看门狗num天"),
    BUY_ROBOT(10, "购买机器人", "成功购买机器人num天"),
    EXCHANGE_GRADE(11, "兑换积分", "成功兑换积分"),
    FEED_DUCK(12, "喂鸭", "成功喂鸭"),
    EGG_HOT(13, "给蛋加温", "成功给蛋加温"),
    BREEDING_EGG(14, "孵蛋", "成功放入num个蛋孵化"),
    BREEDING_DUCK(15, "养鸭", "成功放入num个鸭入鸭场养殖"),
    ORDER_SELL_DUCK(16, "挂单出售商品鸭", "挂单出售num个鸭"),
    ORDER_SELL_EGG(17, "挂单出售商品蛋", "挂单出售num个鸭蛋"),
    MASTER_EGG_HOT(18, "给师父蛋加温", "成功给师父蛋加温"),

    PAY_STEAL_EGG(19, "支付偷蛋费用", "成功支付偷蛋费用"),
    PAY_STEAL_DUCK(20, "支付偷鸭费用", "成功支付偷鸭费用"),
    STEAL_EGG_OTHER_WAREHOUSE(21, "偷蛋", "成功偷到num个鸭蛋，已放入您的仓库"),
    STEAL_EGG_BYOTHER_WAREHOUSE(22, "被偷蛋", "您的蛋未及时收获，被玩家偷走20%"),

    STEAL_DUCK_OTHER_WAREHOUSE(23, "偷鸭", "成功偷到num只鸭，已放入您的仓库"),
    STEAL_DUCK_BYOTHER_WAREHOUSE(24, "被偷鸭", "您的鸭未及时收获，被玩家偷走20%"),

    HARVEST_EGG(25,"收获蛋","成功收获num个蛋"),
    HARVEST_DUCK(26,"收获鸭","成功收获num只鸭"),

    EGG_CHANGE(27, "鸭蛋兑换积分", "成功兑换num个鸭蛋"),
    DUCK_CHANGE(28, "鸭兑换积分", "成功兑换num个鸭");
    ;
    private int type;
    private String desc;
    private String remarks;

    public int getType() {
        return type;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    NoticeType(int type, String desc, String remarks) {
        this.type = type;
        this.desc = desc;
        this.remarks = remarks;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
