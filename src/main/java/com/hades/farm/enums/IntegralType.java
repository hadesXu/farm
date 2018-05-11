package com.hades.farm.enums;

/**
 * Created by xiaoxu on 2018/4/27.
 */
public enum IntegralType {
    ONE(1, "菜币获取"),
    TWO(2, "菜币消费"),
    THREE(3, "菜票兑换菜币"),
    FOUR(4, "商品兑换菜币");

    private int type;
    private String desc;

    IntegralType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static IntegralType getType(int key) {
        IntegralType[] types = IntegralType.values();
        for (IntegralType type : types) {
            if (key == type.type) {
                return type;
            }
        }
        return ONE;
    }

    public int getType() {
        return type;
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
