package com.hades.farm.result;

/**
 * Created by xiaoxu on 2018/3/9.
 */
public enum ErrorCode {
    ADD_ERR(1001, "数据插入失败，请稍后再试"),
    UPDATE_ERR(1002, "数据更新失败，请稍后再试"),
    SYSTEM_ERROR(1000, "当前系统错误，请稍后再试");

    /**
     * 错误码
     */
    private int code;
    /**
     * 错误信息
     */
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorCode get(int code) {
        ErrorCode[] values = ErrorCode.values();
        for (ErrorCode value : values) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
