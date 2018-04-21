package com.hades.farm.api.view.response;

/**
 * Created by xiaoxu on 2018/4/6.
 */
public class DealRecordModel {

    /**
     * 添加时间
     */
    private long addTime;

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

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
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
