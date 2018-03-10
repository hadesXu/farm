package com.hades.farm.api.view.response;

/**
 * Created by xiaoxu on 2018/3/10.
 */
public class UserModel {
    /**
     * 用户id
     */
    private long userId;
    /**
     * 昵称
     */
    private String nick;
    /**
     * 头像
     */
    private String face;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 登陆token
     */
    private String token;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
