package com.hades.farm.api.view.response;

/**
 * Created by zhengzl on 2018/4/10.
 */
public class StealModel {
    private long userId;
    private int canStealNum;
    private int harvestNum;
    private int name;
    private String imgUrl;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getCanStealNum() {
        return canStealNum;
    }

    public void setCanStealNum(int canStealNum) {
        this.canStealNum = canStealNum;
    }

    public int getHarvestNum() {
        return harvestNum;
    }

    public void setHarvestNum(int harvestNum) {
        this.harvestNum = harvestNum;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
