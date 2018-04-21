package com.hades.farm.enums;

/**
 * Created by xiaoxu on 2018/4/21.
 */
public enum WithdrawStatus {
    提现中("1", "提现中"),
    提现成功("2", "提现成功"),
    提现失败("3", "提现失败"),;

    private String status;
    private String desc;

    public static WithdrawStatus getStatus(String status) {
        WithdrawStatus[] types = WithdrawStatus.values();
        for (WithdrawStatus type : types) {
            if (status.equals(type.status)) {
                return type;
            }
        }
        return 提现中;
    }


    WithdrawStatus(String status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
