package com.hades.farm.core.data.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TBackReward {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_back_reward.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_back_reward.user_id
     *
     * @mbggenerated
     */
    private Long userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_back_reward.type
     *
     * @mbggenerated
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_back_reward.num
     *
     * @mbggenerated
     */
    private Integer num;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_back_reward.amount
     *
     * @mbggenerated
     */
    private BigDecimal amount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_back_reward.if_back
     *
     * @mbggenerated
     */
    private String ifBack;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_back_reward.source
     *
     * @mbggenerated
     */
    private Long source;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_back_reward.add_time
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_back_reward.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_back_reward
     *
     * @mbggenerated
     */
    public TBackReward(){}
    public TBackReward(Long id, Long userId, String type, Integer num, BigDecimal amount, String ifBack, Long source, Date addTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.num = num;
        this.amount = amount;
        this.ifBack = ifBack;
        this.source = source;
        this.addTime = addTime;
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_back_reward.id
     *
     * @return the value of t_back_reward.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_back_reward.user_id
     *
     * @return the value of t_back_reward.user_id
     *
     * @mbggenerated
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_back_reward.type
     *
     * @return the value of t_back_reward.type
     *
     * @mbggenerated
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_back_reward.num
     *
     * @return the value of t_back_reward.num
     *
     * @mbggenerated
     */
    public Integer getNum() {
        return num;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_back_reward.amount
     *
     * @return the value of t_back_reward.amount
     *
     * @mbggenerated
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_back_reward.if_back
     *
     * @return the value of t_back_reward.if_back
     *
     * @mbggenerated
     */
    public String getIfBack() {
        return ifBack;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_back_reward.source
     *
     * @return the value of t_back_reward.source
     *
     * @mbggenerated
     */
    public Long getSource() {
        return source;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_back_reward.add_time
     *
     * @return the value of t_back_reward.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_back_reward.update_time
     *
     * @return the value of t_back_reward.update_time
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

    public void setType(String type) {
        this.type = type;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setIfBack(String ifBack) {
        this.ifBack = ifBack;
    }

    public void setSource(Long source) {
        this.source = source;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}