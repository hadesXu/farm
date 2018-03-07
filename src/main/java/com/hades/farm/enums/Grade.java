package com.hades.farm.enums;

/**
 * 等级枚举
 * Created by xiaoxu on 2018/3/7.
 */
public enum Grade {
    MENTOR(1, "师傅"),
    ADVISER(2, "师爷"),
    LANDLORD(3, "地主"),
    PARTNER(4, "合伙人"),
    AGENCY(5, "代理"),
    APPRENTICE(0, "徒弟");


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
