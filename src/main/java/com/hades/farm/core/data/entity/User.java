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
     * 性别
     */
    private int sex;

    /**
     * 生日
     */
    private long birth;

    /**
     * 是否实名认证 1.否 2.是
     */
    private int isAuth;

    /**
     * 微信key
     */
    private String wechat;

    /**
     * 推荐人ID
     */
    private Long parentId;

    /**
     * 3代Id集合,用逗号隔开 父亲,爷爷,曾祖父
     */
    private String parents;

    /**
     * 是否是集团用户    值为1（否）、2（是）。默认为否。
     */
    private int isGroup;

    /**
     * 代数
     */
    private int generation;

    /**
     * 集团boss ID
     */
    private long groupBossId;

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
    private Integer sEgg;

    /**
     * 是否有偷鸭权限 值为1（否）、2（是）。默认为否。
     */
    private Integer sDuck;

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

    private Date dogEndDay;

    private Date robotEndDay;

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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public long getBirth() {
        return birth;
    }

    public void setBirth(long birth) {
        this.birth = birth;
    }

    public int getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(int isAuth) {
        this.isAuth = isAuth;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParents() {
        return parents;
    }

    public void setParents(String parents) {
        this.parents = parents;
    }

    public int getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(int isGroup) {
        this.isGroup = isGroup;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public long getGroupBossId() {
        return groupBossId;
    }

    public void setGroupBossId(long groupBossId) {
        this.groupBossId = groupBossId;
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

    public Integer getsEgg() {
        return sEgg;
    }

    public void setsEgg(Integer sEgg) {
        this.sEgg = sEgg;
    }

    public Integer getsDuck() {
        return sDuck;
    }

    public void setsDuck(Integer sDuck) {
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

    public Date getDogEndDay() {
        return dogEndDay;
    }

    public void setDogEndDay(Date dogEndDay) {
        this.dogEndDay = dogEndDay;
    }

    public Date getRobotEndDay() {
        return robotEndDay;
    }

    public void setRobotEndDay(Date robotEndDay) {
        this.robotEndDay = robotEndDay;
    }
}

