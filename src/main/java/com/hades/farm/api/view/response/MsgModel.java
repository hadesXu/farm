package com.hades.farm.api.view.response;

/**
 * Created by zhengzl on 2018/3/24.
 */
public class MsgModel {
    private int code;
    private String message;

    public MsgModel(int code,String message){
       this.code = code;
        this.message = message;
    }

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
