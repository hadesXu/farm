package com.hades.farm.core.data.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TDuckWarehouse {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_duck_warehouse.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_duck_warehouse.user_id
     *
     * @mbggenerated
     */
    private Long userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_duck_warehouse.duck
     *
     * @mbggenerated
     */
    private Integer duck;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_duck_warehouse.duck_doing
     *
     * @mbggenerated
     */
    private Integer duckDoing;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_duck_warehouse.egg
     *
     * @mbggenerated
     */
    private Integer egg;



    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_duck_warehouse.if_harvest
     *
     * @mbggenerated
     */
    private Integer ifHarvest;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_duck_warehouse.if_steal
     *
     * @mbggenerated
     */
    private Integer ifSteal;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_duck_warehouse.all_sell
     *
     * @mbggenerated
     */
    private Integer allSell;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_duck_warehouse.all_profit
     *
     * @mbggenerated
     */
    private BigDecimal allProfit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_duck_warehouse.all_integral
     *
     * @mbggenerated
     */
    private BigDecimal allIntegral;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_duck_warehouse.food
     *
     * @mbggenerated
     */
    private Integer food;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_duck_warehouse.add_time
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_duck_warehouse.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_duck_warehouse
     *
     * @mbggenerated
     */
    public TDuckWarehouse(){}
    public TDuckWarehouse(Long id, Long userId, Integer duck, Integer duckDoing, Integer egg, Integer ifHarvest, Integer ifSteal, Integer allSell, BigDecimal allProfit, BigDecimal allIntegral, Integer food, Date addTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.duck = duck;
        this.duckDoing = duckDoing;
        this.egg = egg;
        this.ifHarvest = ifHarvest;
        this.ifSteal = ifSteal;
        this.allSell = allSell;
        this.allProfit = allProfit;
        this.allIntegral = allIntegral;
        this.food = food;
        this.addTime = addTime;
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_duck_warehouse.id
     *
     * @return the value of t_duck_warehouse.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_duck_warehouse.user_id
     *
     * @return the value of t_duck_warehouse.user_id
     *
     * @mbggenerated
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_duck_warehouse.duck
     *
     * @return the value of t_duck_warehouse.duck
     *
     * @mbggenerated
     */
    public Integer getDuck() {
        return duck;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_duck_warehouse.duck_doing
     *
     * @return the value of t_duck_warehouse.duck_doing
     *
     * @mbggenerated
     */
    public Integer getDuckDoing() {
        return duckDoing;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_duck_warehouse.egg
     *
     * @return the value of t_duck_warehouse.egg
     *
     * @mbggenerated
     */
    public Integer getEgg() {
        return egg;
    }


    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_duck_warehouse.if_harvest
     *
     * @return the value of t_duck_warehouse.if_harvest
     *
     * @mbggenerated
     */
    public Integer getIfHarvest() {
        return ifHarvest;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_duck_warehouse.if_steal
     *
     * @return the value of t_duck_warehouse.if_steal
     *
     * @mbggenerated
     */
    public Integer getIfSteal() {
        return ifSteal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_duck_warehouse.all_sell
     *
     * @return the value of t_duck_warehouse.all_sell
     *
     * @mbggenerated
     */
    public Integer getAllSell() {
        return allSell;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_duck_warehouse.all_profit
     *
     * @return the value of t_duck_warehouse.all_profit
     *
     * @mbggenerated
     */
    public BigDecimal getAllProfit() {
        return allProfit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_duck_warehouse.all_integral
     *
     * @return the value of t_duck_warehouse.all_integral
     *
     * @mbggenerated
     */
    public BigDecimal getAllIntegral() {
        return allIntegral;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_duck_warehouse.food
     *
     * @return the value of t_duck_warehouse.food
     *
     * @mbggenerated
     */

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_duck_warehouse.add_time
     *
     * @return the value of t_duck_warehouse.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_duck_warehouse.update_time
     *
     * @return the value of t_duck_warehouse.update_time
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

    public void setDuck(Integer duck) {
        this.duck = duck;
    }

    public void setDuckDoing(Integer duckDoing) {
        this.duckDoing = duckDoing;
    }

    public void setEgg(Integer egg) {
        this.egg = egg;
    }


    public void setIfHarvest(Integer ifHarvest) {
        this.ifHarvest = ifHarvest;
    }

    public void setIfSteal(Integer ifSteal) {
        this.ifSteal = ifSteal;
    }

    public void setAllSell(Integer allSell) {
        this.allSell = allSell;
    }

    public void setAllProfit(BigDecimal allProfit) {
        this.allProfit = allProfit;
    }

    public void setAllIntegral(BigDecimal allIntegral) {
        this.allIntegral = allIntegral;
    }


    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getFood() {
        return food;
    }

    public void setFood(Integer food) {
        this.food = food;
    }
}