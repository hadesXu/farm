package com.hades.farm.api.view.request;

/**
 * Created by xiaoxu on 2018/3/10.
 */
public class CodeRequest {
    /**
     * 手机号
     */
    private String phone;

    /**
     * 微信key
     */
    private String wechat;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }
}
