package com.hades.farm.enums;

/**
 * Created by zhengzl on 2018/3/10.
 */
public enum GoodsType {
    EGG(1, "蛋"),
    DUCK(2, "鸭"),
    FEED(3, "饲料"),
    DOOR_DOG(4,"看门狗"),
    ROBOT(5,"机器人");
    private int type;
    private String desc;
    GoodsType(int type, String desc) {
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
