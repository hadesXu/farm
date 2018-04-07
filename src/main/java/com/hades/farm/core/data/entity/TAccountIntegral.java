package com.hades.farm.core.data.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TAccountIntegral {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account_integral.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account_integral.user_id
     *
     * @mbggenerated
     */
    private Long userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account_integral.balance
     *
     * @mbggenerated
     */
    private BigDecimal balance;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account_integral.acc_gain
     *
     * @mbggenerated
     */
    private BigDecimal accGain;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account_integral.acc_consume
     *
     * @mbggenerated
     */
    private BigDecimal accConsume;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account_integral.add_time
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account_integral.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    public TAccountIntegral() {
    }

    public TAccountIntegral(Long userId, BigDecimal balance, BigDecimal accGain, BigDecimal accConsume, Date addTime) {
        this.userId = userId;
        this.balance = balance;
        this.accGain = accGain;
        this.accConsume = accConsume;
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_account_integral
     *
     * @mbggenerated
     */
    public TAccountIntegral(Long id, Long userId, BigDecimal balance, BigDecimal accGain, BigDecimal accConsume, Date addTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
        this.accGain = accGain;
        this.accConsume = accConsume;
        this.addTime = addTime;
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_account_integral.id
     *
     * @return the value of t_account_integral.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_account_integral.user_id
     *
     * @return the value of t_account_integral.user_id
     *
     * @mbggenerated
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_account_integral.balance
     *
     * @return the value of t_account_integral.balance
     *
     * @mbggenerated
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_account_integral.acc_gain
     *
     * @return the value of t_account_integral.acc_gain
     *
     * @mbggenerated
     */
    public BigDecimal getAccGain() {
        return accGain;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_account_integral.acc_consume
     *
     * @return the value of t_account_integral.acc_consume
     *
     * @mbggenerated
     */
    public BigDecimal getAccConsume() {
        return accConsume;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_account_integral.add_time
     *
     * @return the value of t_account_integral.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_account_integral.update_time
     *
     * @return the value of t_account_integral.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setAccGain(BigDecimal accGain) {
        this.accGain = accGain;
    }

    public void setAccConsume(BigDecimal accConsume) {
        this.accConsume = accConsume;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}