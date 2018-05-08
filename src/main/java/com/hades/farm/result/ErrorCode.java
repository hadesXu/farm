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
    ARGUMENTS_ERROR(1006, "参数错误"),
    NO_LOGIN(1007, "当前用户登录异常,请重新登录"),
    ARGUMENTS_ERROR2(1008, "余额不足"),


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
    PRACTICE_NOWARM_SELF(2010, "实习期" + Constant.PRACTICE_DAY + "天内不能给自己加温"),
    HAS_WARM(2011, "今日已加温"),
    NO_MASTER(2012, "您没有师父"),
    MASTER_NO_EGG(2013, "师父未养蛋"),
    NO_DUCK_DOING(2014, "没有待喂养的鸭"),
    DUCK_DOING_NUM_NOT_MATCH(2015, "待喂养的鸭的数量不匹配，请联系客服"),
    FOOD_NOT_ENOUGH(2016, "饲料不够，请先购买饲料"),
    HAS_FEED(2017, "今日已喂养"),
    BALANCE_NOT_ERR(2018, "您的余额不足哦~"),
    NUM_ILLEGAL(2019, "输入数量非法"),
    HAS_PAY(2020, "已经支付"),
    CANNT_STEAL(2021, "该用户已经被偷"),
    STEAL_TIME_ERROR(2022, "偷鸭时间为：18:00-21:00"),
    BUY_EGG_LIMIT(2023, "数量需在10~200之间"),
    BUY_DUCK_LIMIT(2024, "数量需在20~200之间"),
    NO_HARVEST_EGG(2025, "没有可收获的蛋"),
    NO_HARVEST_DUCK(2026, "没有可收获的鸭"),
    DREEDING_LIMIT(2027, "每日上线200，您已超限"),
    SELL_GOOD_TIME_ERROR(2028, "挂单时间为：09:00-17:30"),
    CANNT_STEAL2(2029, "您今天已经偷过，请明天再来"),
    BREEDING_TIME_ERROR(2030, "0~4点不允许加温及养殖"),

    PHONE_FORMAT_ERROR(3001, "手机号格式错误"),
    PWD_FORMAT_ERROR(3002, "密码不能包含特殊字符"),
    PHONE_EXIST(3003, "手机号已注册"),
    WECHAT_EXIST(3004, "微信号已注册"),
    PHONE_CODE_REQUEST_FREQ(3005, "验证码请求太频繁"),
    PHONE_CODE_INVALID(3006, "验证码错误"),
    USER_NOT_EXIST(3007, "用户不存在"),
    PASSWORD_INVALID(3008, "密码不正确"),
    RELATION_NOT_EXIST(3009, "关系不存在"),
    ITEM_NOT_EXIST(3010, "商品不存在"),
    INTEGRAL_ERROR(3011, "积分兑换不能小于10"),
    WITHDRAW_ERROR(3012, "请在规定时间内提现,谢谢合作！"),
    WITHDRAW_COUNT_ERROR(3013, "每天只能申请一次提现！"),
    AUTH_USER_INVALID(3014, "您的身份信息异常，请确认无误后再次提交!"),
    AUTH_ERROR(3015, "请实名认证！"),
    AUTH_USER_EXIST(3016, "身份信息不能认证多次"),

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
