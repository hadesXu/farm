package com.hades.farm.core.data.entity;

import java.util.Date;

/**
 * token
 * Created by xiaoxu on 2018/3/7.
 */
public class UserToken {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 登录token
     */
    private String token;

    /**
     * 更新时间
     */
    private Date updateTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
