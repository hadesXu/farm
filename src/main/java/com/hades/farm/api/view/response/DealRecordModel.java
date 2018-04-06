package com.hades.farm.api.view.response;

/**
 * Created by xiaoxu on 2018/4/6.
 */
public class DealRecordModel {

    /**
     * 时间描述
     */
    private String timeStr;

    /**
     * 类型
     */
    private String typeStr;

    /**
     * 值描述
     */
    private String valueStr;

    /**
     * 描述
     */
    private String desc;

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

    public String getValueStr() {
        return valueStr;
    }

    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
