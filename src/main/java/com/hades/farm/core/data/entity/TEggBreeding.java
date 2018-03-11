package com.hades.farm.core.data.entity;

import java.util.Date;

public class TEggBreeding {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_egg_breeding.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_egg_breeding.user_id
     *
     * @mbggenerated
     */
    private Long userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_egg_breeding.num
     *
     * @mbggenerated
     */
    private Integer num;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_egg_breeding.num_harvest
     *
     * @mbggenerated
     */
    private Integer numHarvest;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_egg_breeding.day
     *
     * @mbggenerated
     */
    private Integer day;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_egg_breeding.acc_no_hot
     *
     * @mbggenerated
     */
    private Integer accNoHot;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_egg_breeding.status
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_egg_breeding.add_time
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_egg_breeding.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_egg_breeding
     *
     * @mbggenerated
     */
    public TEggBreeding(){}
    public TEggBreeding(Long id, Long userId, Integer num, Integer numHarvest, Integer day, Integer accNoHot, Integer status, Date addTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.num = num;
        this.numHarvest = numHarvest;
        this.day = day;
        this.accNoHot = accNoHot;
        this.status = status;
        this.addTime = addTime;
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_egg_breeding.id
     *
     * @return the value of t_egg_breeding.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_egg_breeding.user_id
     *
     * @return the value of t_egg_breeding.user_id
     *
     * @mbggenerated
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_egg_breeding.num
     *
     * @return the value of t_egg_breeding.num
     *
     * @mbggenerated
     */
    public Integer getNum() {
        return num;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_egg_breeding.num_harvest
     *
     * @return the value of t_egg_breeding.num_harvest
     *
     * @mbggenerated
     */
    public Integer getNumHarvest() {
        return numHarvest;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_egg_breeding.day
     *
     * @return the value of t_egg_breeding.day
     *
     * @mbggenerated
     */
    public Integer getDay() {
        return day;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_egg_breeding.acc_no_hot
     *
     * @return the value of t_egg_breeding.acc_no_hot
     *
     * @mbggenerated
     */
    public Integer getAccNoHot() {
        return accNoHot;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_egg_breeding.status
     *
     * @return the value of t_egg_breeding.status
     *
     * @mbggenerated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_egg_breeding.add_time
     *
     * @return the value of t_egg_breeding.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_egg_breeding.update_time
     *
     * @return the value of t_egg_breeding.update_time
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

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setNumHarvest(Integer numHarvest) {
        this.numHarvest = numHarvest;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public void setAccNoHot(Integer accNoHot) {
        this.accNoHot = accNoHot;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}