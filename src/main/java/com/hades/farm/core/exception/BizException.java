package com.hades.farm.core.exception;

import com.hades.farm.result.ErrorCode;

/**
 * Created by zhengzl on 2018/3/9.
 */
public class BizException extends Exception{
    private int errCode;
    private String errMessage;

    public  BizException(int errCode,String errMessage){
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    public  BizException(ErrorCode errorCode){
        this.errCode = errorCode.getCode();
        this.errMessage = errorCode.getMessage();
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
