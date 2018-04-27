package com.hades.farm.api.view.response;

/**
 * Created by xiaoxu on 2018/4/27.
 */
public class VendibilityModel {

    /**
     * 可出售的商品鸭数量
     */
    private int duckNum;
    /**
     * 可出售的商品蛋数量
     */
    private int eggNum;

    public int getDuckNum() {
        return duckNum;
    }

    public void setDuckNum(int duckNum) {
        this.duckNum = duckNum;
    }

    public int getEggNum() {
        return eggNum;
    }

    public void setEggNum(int eggNum) {
        this.eggNum = eggNum;
    }
}
