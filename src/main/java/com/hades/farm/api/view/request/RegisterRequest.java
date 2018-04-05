package com.hades.farm.api.view.request;

import javax.validation.constraints.NotNull;

/**
 * Created by xiaoxu on 2018/3/9.
 */
public class RegisterRequest {
    /**
     * 手机号
     */
    @NotNull
    private String phone;

    /**
     * 手机验证码
     */
    @NotNull
    private String code;

    /**
     * 密码
     */
    @NotNull
    private String pwd;

    /**
     * 微信
     */
    @NotNull
    private String wechat;

    /**
     * 昵称
     */
    private String name;

    /**
     * 图片
     */
    private String imgUrl;

    /**
     * 父ID
     */
    private Long parentId;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
