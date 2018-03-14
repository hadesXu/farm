package com.hades.farm.core.data.entity;

import java.util.Date;

/**
 * Created by xiaoxu on 2018/3/4.
 */
public class User {

    /**
     * 用户ID
     */
    private long id;

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 用户昵称
     */
    private String name;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 微信key
     */
    private String wechat;

    /**
     * 等级{@link com.hades.farm.enums.Grade}
     */
    private int grade;

    /**
     * 是否实习 值为1（否）、2（是）。默认为否。
     */
    private int isTrainee;

    /**
     * 是否有偷蛋权限 值为1（否）、2（是）。默认为否。
     */
    private int sEgg;

    /**
     * 是否有偷鸭权限 值为1（否）、2（是）。默认为否。
     */
    private int sDuck;

    /**
     * 是否有效 值为1（无效）、2（有效）。默认为有效。
     */
    private int active;

    /**
     * 头像地址
     */
    private String imgUrl;

    /**
     * 二维码
     */
    private String qrCode;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date addTime;

    private Long parentId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getIsTrainee() {
        return isTrainee;
    }

    public void setIsTrainee(int isTrainee) {
        this.isTrainee = isTrainee;
    }

    public int getsEgg() {
        return sEgg;
    }

    public void setsEgg(int sEgg) {
        this.sEgg = sEgg;
    }

    public int getsDuck() {
        return sDuck;
    }

    public void setsDuck(int sDuck) {
        this.sDuck = sDuck;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}

