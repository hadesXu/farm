package com.hades.farm.api.view.request;

import javax.validation.constraints.NotNull;

/**
 * Created by xiaoxu on 2018/3/26.
 */
public class RetrievePwdRequest {

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
    private String pwd;

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
}
