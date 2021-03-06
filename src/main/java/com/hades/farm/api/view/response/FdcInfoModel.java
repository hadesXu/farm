package com.hades.farm.api.view.response;

import com.hades.farm.core.data.entity.TEggBreeding;

import java.util.List;

/**
 * Created by zhengzl on 2018/4/5.
 */
public class FdcInfoModel {
    private int eggDoing;//正在孵化的蛋
    private int egg;//待孵化的蛋
    private int hasDog;//是否有狗
    private int ifSteal;//今日是否已偷
    private int sEgg;//是否有偷蛋权限
    private int hasRobot;//是否有机器人
    private String dogEndDay;
    private String robotEndDay;
    private String ifHot;//今日是否加温
    private int havestNum;//待收获数量
    private List<TEggBreeding> eggBreedingList;

    public String getIfHot() {
        return ifHot;
    }

    public void setIfHot(String ifHot) {
        this.ifHot = ifHot;
    }

    public String getRobotEndDay() {
        return robotEndDay;
    }

    public void setRobotEndDay(String robotEndDay) {
        this.robotEndDay = robotEndDay;
    }

    public String getDogEndDay() {
        return dogEndDay;
    }

    public void setDogEndDay(String dogEndDay) {
        this.dogEndDay = dogEndDay;
    }

    public int getEggDoing() {
        return eggDoing;
    }

    public void setEggDoing(int eggDoing) {
        this.eggDoing = eggDoing;
    }

    public int getEgg() {
        return egg;
    }

    public void setEgg(int egg) {
        this.egg = egg;
    }

    public int getHasDog() {
        return hasDog;
    }

    public void setHasDog(int hasDog) {
        this.hasDog = hasDog;
    }

    public int getIfSteal() {
        return ifSteal;
    }

    public void setIfSteal(int ifSteal) {
        this.ifSteal = ifSteal;
    }

    public int getsEgg() {
        return sEgg;
    }

    public void setsEgg(int sEgg) {
        this.sEgg = sEgg;
    }

    public int getHasRobot() {
        return hasRobot;
    }

    public void setHasRobot(int hasRobot) {
        this.hasRobot = hasRobot;
    }

    public int getHavestNum() {
        return havestNum;
    }

    public void setHavestNum(int havestNum) {
        this.havestNum = havestNum;
    }

    public List<TEggBreeding> getEggBreedingList() {
        return eggBreedingList;
    }

    public void setEggBreedingList(List<TEggBreeding> eggBreedingList) {
        this.eggBreedingList = eggBreedingList;
    }
}
