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
    private Long birth;

    /**
     * 性别
     */
    private Integer sex;

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

    public Long getBirth() {
        return birth;
    }

    public void setBirth(Long birth) {
        this.birth = birth;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
}
