package com.hades.farm.api.view.request;

/**
 * Created by xiaoxu on 2018/3/9.
 */
public class RegisterRequest {
    /**
     * 手机号
     */
    private String phone;

    /**
     * 手机验证码
     */
    private String code;

    /**
     * 密码
     */
    String pwd;

    /**
     * 微信
     */
    private String wechat;

    /**
     * 父ID
     */
    String fatherNumber;

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

    public String getFatherNumber() {
        return fatherNumber;
    }

    public void setFatherNumber(String fatherNumber) {
        this.fatherNumber = fatherNumber;
    }
}
