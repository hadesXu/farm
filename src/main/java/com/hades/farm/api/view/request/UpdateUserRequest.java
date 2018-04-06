package com.hades.farm.api.view.request;

/**
 * Created by xiaoxu on 2018/4/6.
 */
public class UpdateUserRequest {

    /**
     * 用户ID
     */
    private long userId;

    /**
     * 生日
     */
    private long birth;

    /**
     * 性别
     */
    private int sex;

    /**
     * QQ号
     */
    private String qq;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getBirth() {
        return birth;
    }

    public void setBirth(long birth) {
        this.birth = birth;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
}
