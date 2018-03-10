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
     * 随机字符串
     */
    private String randomString;
    /**
     * 时间戳
     */
    private long time;
    /**
     * 签名
     */
    private String sign;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRandomString() {
        return randomString;
    }

    public void setRandomString(String randomString) {
        this.randomString = randomString;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}
