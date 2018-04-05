package com.hades.farm.api.view.response;

import java.math.BigDecimal;

/**
 * Created by xiaoxu on 2018/4/6.
 */
public class RecordModel {

    /**
     * 添加时间
     */
    private String timeStr;

    /**
     * 类型描述
     */
    private String typeStr;

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
