package com.hades.farm.enums;

/**
 * Created by zhengzl on 2018/3/19.
 */
public enum WareHouseOperType {

    SELL(1,"卖出"),
    BUY(2,"回收"),
    BACK_ADD(3,"后台增加"),
    BACK_SUBTRACT(4,"后台减少"),
    ;

    private int type;
    private String desc;
    WareHouseOperType(int type,String desc){
        this.type = type;
        this.desc  = desc;
    }
    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
