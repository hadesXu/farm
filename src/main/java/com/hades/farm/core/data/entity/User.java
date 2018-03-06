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
    private String name;

    /**
     * 用户密码
     */
    private String password;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
