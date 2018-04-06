package com.hades.farm.api.view.response;

/**
 * Created by xiaoxu on 2018/3/10.
 */
public class UserDetailModel {

    /**
     * 用户ID
     */
    private long userId;

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 用户昵称
     */
    private String name;

    /**
     * 性别
     */
    private int sex;

    /**
     * 生日
     */
    private long birth;

    /**
     * QQ
     */
    private String qq;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
}
