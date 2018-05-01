package com.hades.farm.api.view.request;

import java.math.BigDecimal;

/**
 * Created by xiaoxu on 2018/4/6.
 */
public class WithdrawRequest {

    /**
     * 用户ID
     */
    private long userId;

    /**
     * 提现类型
     */
    private Integer type;

    /**
     * 提现金额
     */
    private BigDecimal amount;

    /**
     * 开户行名称
     */
    private String bankName;

    /**
     * 支行
     */
    private String branchName;

    /**
     * 支付宝账号
     */
    private String alipayAccount;

    /**
     * 开户卡号
     */
    private String cardNo;

    /**
     * 验证码
     */
    private String code;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAlipayAccount() {
        return alipayAccount;
    }

    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
