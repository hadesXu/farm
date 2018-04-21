package com.hades.farm.api.view.response;

import java.math.BigDecimal;

/**
 * Created by xiaoxu on 2018/4/21.
 */
public class WithdrawRecordModel {

    /**
     * 添加时间
     */
    private long addTime;

    /**
     * 提现状态描述
     */
    private String stateStr;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 账号
     */
    private String no;

    /**
     * 类型
     */
    private String typeStr;

    /**
     * 值
     */
    private BigDecimal value;

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public String getStateStr() {
        return stateStr;
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
