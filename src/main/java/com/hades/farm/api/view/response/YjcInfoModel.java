package com.hades.farm.api.view.response;

import java.math.BigDecimal;

/**
 * Created by zhengzl on 2018/3/24.
 */
public class YjcInfoModel {
    private int duckDoing;//生产中的鸭
    private int duck;//种鸭
    private int food;
    private int hasDog;
    private int ifSteal;
    private int sDuck;


    public YjcInfoModel(){}
    public int getDuckDoing() {
        return duckDoing;
    }

    public void setDuckDoing(int duckDoing) {
        this.duckDoing = duckDoing;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getHasDog() {
        return hasDog;
    }

    public void setHasDog(int hasDog) {
        this.hasDog = hasDog;
    }

    public int getDuck() {
        return duck;
    }

    public void setDuck(int duck) {
        this.duck = duck;
    }

    public int getIfSteal() {
        return ifSteal;
    }

    public void setIfSteal(int ifSteal) {
        this.ifSteal = ifSteal;
    }

    public int getsDuck() {
        return sDuck;
    }

    public void setsDuck(int sDuck) {
        this.sDuck = sDuck;
    }
}
