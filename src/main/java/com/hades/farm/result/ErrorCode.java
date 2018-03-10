package com.hades.farm.result;

/**
 * Created by xiaoxu on 2018/3/9.
 */
public enum ErrorCode {
    SYSTEM_ERROR(1000, "当前系统错误，请稍后再试"),
    ADD_ERR(1001, "数据插入失败，请稍后再试"),
    UPDATE_ERR(1002, "数据更新失败，请稍后再试"),
    INVALID_REQUEST(1003, "非法请求"),
    ARGUMENTS_NULL(1004, "参数不能为空"),


    EGG_NO_ENOUGH(2000, "鸭蛋数量不足"),
    DUCK_NO_ENOUGH(2001, "鸭数量不足"),
    PLATFORM_EGG_NO_ENOUGH(2002, "仓库蛋数量不足"),
    PLATFORM_DUCK_NO_ENOUGH(2003, "仓库鸭数量不足"),
    PLATFORM_DOG_NO_ENOUGH(2004, "仓库狗数量不足"),
    TICKET_NO_ENOUGH(2005, "菜票不足，请充值"),


    PHONE_FORMAT_ERROR(3001, "手机号格式错误"),
    PWD_FORMAT_ERROR(3002, "密码不能包含特殊字符"),
    PHONE_EXIST(3003, "手机号已注册"),
    WECHAT_EXIST(3004, "微信号已注册"),
    PHONE_CODE_REQUEST_FREQ(3005, "验证码请求太频繁"),
    PHONE_CODE_INVALID(10007, "验证码错误"),
    USER_NOT_EXIST(10008, "用户不存在"),
    PASSWORD_INVALID(10009, "密码不正确"),


    SUCCESS(0000, "成功");

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
