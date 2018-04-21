package com.hades.farm.api.view.response;

import java.math.BigDecimal;

/**
 * Created by xiaoxu on 2018/4/21.
 */
public class WithdrawRecordModel {

    /**
     * 添加时间
     */
    private String timeStr;

    /**
     * 提现状态描述
     */
    private String stateStr;

    /**
     * 值
     */
    private BigDecimal value;

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getStateStr() {
        return stateStr;
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
