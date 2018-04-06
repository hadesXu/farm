package com.hades.farm.enums;

public enum Sex {
    UNDEFINED(0, "保密"),
    MALE(1, "男"),
    FEMALE(2, "女");

    private Sex(int type, String tip) {
        this.type = type;
        this.tip = tip;
    }

    public int type;
    public String tip;

    public static Sex getType(int key) {
        Sex[] types = Sex.values();
        for (Sex type : types) {
            if (key == type.type) {
                return type;
            }
        }
        return UNDEFINED;
    }

    public static boolean valid(int key) {
        Sex[] types = Sex.values();
        for (Sex type : types) {
            if (key == type.type) {
                return true;
            }
        }
        return false;
    }
}
