package com.hades.farm.core.result.error;

/**
 * Created by Ben on 16/8/29.
 */
public enum ErrorCode {
    // system
    SYSTEM_ERROR("system_error", "系统错误"),
    ARG_NOT_NULL("arg_not_null", "参数不能为空"),
    UPDATE_FAILED("update_failed","更新操作失败"),
    HTTP_REQUES_FAILED("http_request_failed","http请求失败"),
    // register or login
    PHONE_NO_NULL("phone_no_null","手机号不能为空"),
    PHONE_UNREGISTER("phone_unregister","手机号未注册"),
    REGISTER_ARG_INVALID("register_arg_invalid", "手机,qq和微信不能全为空"),
    PASSWORD_NO_NULL("password_not _null","密码不能为空"),
    PASSWORD_INVALID("password_invalid","密码不正确"),
    // user
    USER_ADD_CANNOT_BE_NULL("user_add_cannot_be_null", "新增用户参数不能为空"),
    USER_ID_INVALID("user_id_invalid", "用户id不正确"),
    USER_NOT_EXIST("user_not_exist", "用户不存在"),
    USER_EXIST("user_not_exist", "用户已存在"),
    USER_STATE_DISABLE("user_state_disable", "该用户已经被禁用,有疑问请联系客服"),
    // user update
    USER_NICK_INVALID("user_nick_invalid","该昵称不可用"),
    USER_NICK_EXIST("user_nick_exist","该昵称已被占用"),
    USER_UPDATE_CANNOT_BE_NULL("user_update_cannot_be_null", "更新用户参数不能为空"),
    USER_UPDATE_FAILED("user_update_failed","用户更新失败"),
    USER_PASSWORD_EQAUL("user_password_equal","密码相同"),
    USER_PASSWD_UPDATE_FAILED("user_passwd_update_failed", "更新密码失败"),
    // user balance
    USER_BALANCE_NOT_EXIST("user_balance_not_exist","用户财富表不存在"),
    BALANCE_COIN_NOT_ENOUGH("balance_coin_not_enough","货币余额不足"),
    BALANCE_INC_FAILED("balance_inc_failed","货币扣费失败"),
    BALANCE_UPDATE_CANNOT_BE_NULL("balance_update_cannot_be_null","货币值更新不能为空"),
    BALANCE_UPDATE_ARG_INVALID("balance_update_arg_invalid","货币值参数值不能全为空"),
    // token
    TOKEN_UPDATE_FAILED("token_update_failed","token更新失败"),
    TOKEN_INSERT_FAILED("token_insert_failed","token插入失败"),
    TOKEN_DELETE_FAILED("token_delete_failed","token删除失败"),
    TOKEN_GENERATE_FAILED("token_generate_failed","token生成失败"),
    // sms
    SEND_SMS_FAILED("send_sms_failed", "发送短信失败"),
    INVALID_PHONE_CODE("invalid_phone_code", "手机验证码不正确"),
    CHECK_CODE_CANNOT_BE_NULL("check_code_cannot_be_null", "验证码不能为空"),
    CHECK_CODE_LESS_THAN_MINUTS("check_code_less_than_minuts","请求验证码太频繁"),

    //item
    ITEM_NOT_EXIST("item_not_exist","商品不存在"),
    UPDATE_NOT_CHANGE("update_not_change","没有更改,不用更新"),
    /**
     * 商品暂时不支持购买
     */
    ITEM_STATE_INVALID("item_state_invalid", "该商品暂不支持购买"),

    //item update
    ITEM_UPDATE_FAILED("item_update_failed","商品更新失败"),
    ITEM_STATE_UPDATE_FAILED("item_state_update_failed","商品状态更新失败"),

    //item add
    ITEM_ADD_FAILED("item_add_failed","商品添加失败"),
    ITEM_BATCHQUANTITY_UPDATE_FAILED("item_batchquantity_update_failed","批量更新商品数量失败"),
    /**
     * 类目不存在
     */
    CAT_NOT_EXIS("cat_not_exist","类目不存在"),

    /**
     * order
     */
    BUY_ITEM_COUNT_CANNOT_ZERO("buy_item_count_zero", "购买数量不能为0"),
    /**
     * 剩余购买人次不够
     */
    BUY_ITEM_COUNT_NOT_ENOUGH("buy_item_count_not_enough", "剩余购买人次不够"),
    /**
     * 订单不存在
     */
    ORDER_NOT_EXIST("order_not_exist", "订单不存在"),
    /**
     * 该期商品不存在
     */
    ITEM_PERIOD_NOT_EXIST("item_period_not_exist", "订单不存在"),

    /**
     * item不存在
     */
    CHARGE_ITEM_NOT_EXIST("charge_item_not_exist", "该分类不存在"),

    /**
     * 收货地址不合法
     */
    USER_ADDRESS_INVALID("user_address_invalid", "用户的收货地址不合法"),
    /**
     * 开奖失败
     */
    OPEN_PRIZE_FAILED("open_prize_failed", "开奖失败"),

    /**
     * 消息发送对象不能为空
     */
    CHAT_SEND_TARGET_NULL("chat_send_target_null", "消息发送对象不能为空")

    ;
    /**
     * 错误码
     */
    private String errCode;
    /**
     * 错误信息
     */
    private String errMsg;

    ErrorCode(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
