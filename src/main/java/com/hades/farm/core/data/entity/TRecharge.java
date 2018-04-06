package com.hades.farm.core.data.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TRecharge {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_recharge.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_recharge.user_id
     *
     * @mbggenerated
     */
    private Long userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_recharge.recharge_no
     *
     * @mbggenerated
     */
    private String rechargeNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_recharge.type
     *
     * @mbggenerated
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_recharge.amount
     *
     * @mbggenerated
     */
    private BigDecimal amount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_recharge.status
     *
     * @mbggenerated
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_recharge.remarks
     *
     * @mbggenerated
     */
    private String remarks;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_recharge.add_time
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_recharge.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;


    public TRecharge() {
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recharge
     *
     * @mbggenerated
     */
    public TRecharge(Long id, Long userId, String rechargeNo, String type, BigDecimal amount, String status, String remarks, Date addTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.rechargeNo = rechargeNo;
        this.type = type;
        this.amount = amount;
        this.status = status;
        this.remarks = remarks;
        this.addTime = addTime;
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_recharge.id
     *
     * @return the value of t_recharge.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_recharge.user_id
     *
     * @return the value of t_recharge.user_id
     *
     * @mbggenerated
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_recharge.recharge_no
     *
     * @return the value of t_recharge.recharge_no
     *
     * @mbggenerated
     */
    public String getRechargeNo() {
        return rechargeNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_recharge.type
     *
     * @return the value of t_recharge.type
     *
     * @mbggenerated
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_recharge.amount
     *
     * @return the value of t_recharge.amount
     *
     * @mbggenerated
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_recharge.status
     *
     * @return the value of t_recharge.status
     *
     * @mbggenerated
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_recharge.remarks
     *
     * @return the value of t_recharge.remarks
     *
     * @mbggenerated
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_recharge.add_time
     *
     * @return the value of t_recharge.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_recharge.update_time
     *
     * @return the value of t_recharge.update_time
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

    public void setRechargeNo(String rechargeNo) {
        this.rechargeNo = rechargeNo;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setStatus(String status) {
        this.status = status;
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
}