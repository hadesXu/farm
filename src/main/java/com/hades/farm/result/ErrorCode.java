package com.hades.farm.result;

/**
 * Created by xiaoxu on 2018/3/9.
 */
public enum ErrorCode {
    SUCCESS(0000,"成功"),
    ADD_ERR(1001, "数据插入失败，请稍后再试"),
    UPDATE_ERR(1002, "数据更新失败，请稍后再试"),
    SYSTEM_ERROR(1000, "当前系统错误，请稍后再试"),
    EGG_NO_ENOUGH(2000,"鸭蛋数量不足"),
    DUCK_NO_ENOUGH(2001,"鸭数量不足"),
    PLATFORM_EGG_NO_ENOUGH(2002,"仓库蛋数量不足"),
    PLATFORM_DUCK_NO_ENOUGH(2003,"仓库鸭数量不足"),
    PLATFORM_DOG_NO_ENOUGH(2004,"仓库狗数量不足"),
    TICKET_NO_ENOUGH(2005,"菜票不足，请充值");

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
