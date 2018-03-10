package com.hades.farm.enums;

/**
 * 等级枚举
 * Created by xiaoxu on 2018/3/7.
 */
public enum Grade {
    MENTOR(2, "师傅"),
    ADVISER(3, "师爷"),
    LANDLORD(4, "地主"),
    PARTNER(5, "合伙人"),
    AGENCY(6, "代理"),
    APPRENTICE(1, "徒弟");


    /**
     * 类型
     */
    public int type;

    /**
     * 描述
     */
    public String desc;

    Grade(int type, String desc) {
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
