package com.hades.farm.result;

import com.hades.farm.utils.Constant;

/**
 * Created by xiaoxu on 2018/3/9.
 */
public enum ErrorCode {
    SYSTEM_ERROR(1000, "当前系统错误，请稍后再试"),
    ADD_ERR(1001, "数据插入失败，请稍后再试"),
    UPDATE_ERR(1002, "数据更新失败，请稍后再试"),
    INVALID_REQUEST(1003, "非法请求"),
    ARGUMENTS_NULL(1004, "参数不能为空"),
    GOOD_TYPE_ERROR(1005, "物品类型错误"),


    EGG_NO_ENOUGH(2000, "鸭蛋数量不足"),
    DUCK_NO_ENOUGH(2001, "鸭数量不足"),
    PLATFORM_EGG_NO_ENOUGH(2002, "仓库蛋数量不足"),
    PLATFORM_DUCK_NO_ENOUGH(2003, "仓库鸭数量不足"),
    PLATFORM_DOG_NO_ENOUGH(2004, "仓库狗数量不足"),
    TICKET_NO_ENOUGH(2005, "菜票不足，请充值"),
    ORDER_ERROR(2006, "未找到对应订单信息"),
    NO_BUY_SELF_ORDER(2007, "不能购买自己发布的订单"),
    BUY_ALLOF_ORDER(2008, "必须全部购买"),
    ORDER_STATUS_ERROR(2009, "订单状态不正确"),
    PRACTICE_NOWARM_SELF(2010, "实习期"+ Constant.PRACTICE_DAY+"内不能给自己加温"),
    HAS_WARM(2011, "今日已加温"),
    NO_MASTER(2012, "您没有师父"),
    MASTER_NO_EGG(2013, "师父未养蛋"),
    NO_DUCK_DOING(2014, "没有待喂养的鸭"),
    DUCK_DOING_NUM_NOT_MATCH(2015, "待喂养的鸭的数量不匹配，请联系客服"),
    FOOD_NOT_ENOUGH(2016, "饲料不够，请先购买饲料"),
    HAS_FEED(2017, "今日已喂养"),


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
