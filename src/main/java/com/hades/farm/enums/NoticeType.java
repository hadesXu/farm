package com.hades.farm.enums;

/**
 * Created by zhengzl on 2018/3/10.
 */
public enum NoticeType {
    FEED_DUCK(1,"喂鸭",""),
    EGG_HOT(2,"给蛋加温",""),
    BREEDING_EGG(3,"孵蛋","成功放入num个蛋孵化")
    ;
    private int type;
    private String desc;
    private String remarks;

    public int getType() {
        return type;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    NoticeType(int type, String desc,String remarks) {
        this.type = type;
        this.desc = desc;
        this.remarks = remarks;
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
