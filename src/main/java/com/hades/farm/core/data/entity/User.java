package com.hades.farm.core.data.entity;

/**
 * Created by xiaoxu on 2018/3/4.
 */
public class User {

    /**
     * 用户ID
     */
    private long id;

    /**
     * 用户昵称
     */
    private String nick;

    /**
     * 用户密码
     */
    private String pwd;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
