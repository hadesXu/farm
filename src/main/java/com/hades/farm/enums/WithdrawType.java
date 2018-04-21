package com.hades.farm.enums;

/**
 * Created by xiaoxu on 2018/4/6.
 */
public enum WithdrawType {
    ALIPAY(1, "支付宝"),
    BANK(2, "银行卡");
    public int type;
    public String tip;

    WithdrawType(int type, String tip) {
        this.type = type;
        this.tip = tip;
    }


    public static WithdrawType getType(int key) {
        WithdrawType[] types = WithdrawType.values();
        for (WithdrawType type : types) {
            if (key == type.type) {
                return type;
            }
        }
        return ALIPAY;
    }

    public static boolean valid(int key) {
        WithdrawType[] types = WithdrawType.values();
        for (WithdrawType type : types) {
            if (key == type.type) {
                return true;
            }
        }
        return false;
    }
}
