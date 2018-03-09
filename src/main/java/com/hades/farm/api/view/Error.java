package com.hades.farm.api.view;

/**
 * Created by xiaoxu on 2018/3/9.
 */
public class Error {
    /**
     * 错误码
     */
    private int code;
    /**
     * 错误信息
     */
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
