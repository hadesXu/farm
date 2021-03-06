package com.hades.farm.core.data.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TWithdraw {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_withdraw.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_withdraw.withdraw_no
     *
     * @mbggenerated
     */
    private String withdrawNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_withdraw.type
     *
     * @mbggenerated
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_withdraw.amount
     *
     * @mbggenerated
     */
    private BigDecimal amount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_withdraw.rate
     *
     * @mbggenerated
     */
    private BigDecimal rate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_withdraw.fee
     *
     * @mbggenerated
     */
    private BigDecimal fee;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_withdraw.interbank
     *
     * @mbggenerated
     */
    private BigDecimal interbank;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_withdraw.status
     *
     * @mbggenerated
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_withdraw.real_name
     *
     * @mbggenerated
     */
    private String realName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_withdraw.alipay_account
     *
     * @mbggenerated
     */
    private String alipayAccount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_withdraw.bank_name
     *
     * @mbggenerated
     */
    private String bankName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_withdraw.branch_name
     *
     * @mbggenerated
     */
    private String branchName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_withdraw.card_no
     *
     * @mbggenerated
     */
    private String cardNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_withdraw.remarks
     *
     * @mbggenerated
     */
    private String remarks;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_withdraw.add_time
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_withdraw.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_withdraw.user_id
     *
     * @mbggenerated
     */
    private Long userId;

    public TWithdraw() {
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_withdraw
     *
     * @mbggenerated
     */
    public TWithdraw(Long id, String withdrawNo, String type, BigDecimal amount, BigDecimal rate, BigDecimal fee, BigDecimal interbank, String status, String realName, String alipayAccount, String bankName, String branchName, String cardNo, String remarks, Date addTime, Date updateTime, Long userId) {
        this.id = id;
        this.withdrawNo = withdrawNo;
        this.type = type;
        this.amount = amount;
        this.rate = rate;
        this.fee = fee;
        this.interbank = interbank;
        this.status = status;
        this.realName = realName;
        this.alipayAccount = alipayAccount;
        this.bankName = bankName;
        this.branchName = branchName;
        this.cardNo = cardNo;
        this.remarks = remarks;
        this.addTime = addTime;
        this.updateTime = updateTime;
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_withdraw.id
     *
     * @return the value of t_withdraw.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_withdraw.withdraw_no
     *
     * @return the value of t_withdraw.withdraw_no
     *
     * @mbggenerated
     */
    public String getWithdrawNo() {
        return withdrawNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_withdraw.type
     *
     * @return the value of t_withdraw.type
     *
     * @mbggenerated
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_withdraw.amount
     *
     * @return the value of t_withdraw.amount
     *
     * @mbggenerated
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_withdraw.rate
     *
     * @return the value of t_withdraw.rate
     *
     * @mbggenerated
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_withdraw.fee
     *
     * @return the value of t_withdraw.fee
     *
     * @mbggenerated
     */
    public BigDecimal getFee() {
        return fee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_withdraw.interbank
     *
     * @return the value of t_withdraw.interbank
     *
     * @mbggenerated
     */
    public BigDecimal getInterbank() {
        return interbank;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_withdraw.status
     *
     * @return the value of t_withdraw.status
     *
     * @mbggenerated
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_withdraw.real_name
     *
     * @return the value of t_withdraw.real_name
     *
     * @mbggenerated
     */
    public String getRealName() {
        return realName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_withdraw.alipay_account
     *
     * @return the value of t_withdraw.alipay_account
     *
     * @mbggenerated
     */
    public String getAlipayAccount() {
        return alipayAccount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_withdraw.bank_name
     *
     * @return the value of t_withdraw.bank_name
     *
     * @mbggenerated
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_withdraw.branch_name
     *
     * @return the value of t_withdraw.branch_name
     *
     * @mbggenerated
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_withdraw.card_no
     *
     * @return the value of t_withdraw.card_no
     *
     * @mbggenerated
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_withdraw.remarks
     *
     * @return the value of t_withdraw.remarks
     *
     * @mbggenerated
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_withdraw.add_time
     *
     * @return the value of t_withdraw.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_withdraw.update_time
     *
     * @return the value of t_withdraw.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_withdraw.user_id
     *
     * @return the value of t_withdraw.user_id
     *
     * @mbggenerated
     */
    public Long getUserId() {
        return userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWithdrawNo(String withdrawNo) {
        this.withdrawNo = withdrawNo;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public void setInterbank(BigDecimal interbank) {
        this.interbank = interbank;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}