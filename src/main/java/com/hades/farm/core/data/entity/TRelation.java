package com.hades.farm.core.data.entity;

import java.util.Date;

public class TRelation {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_relation.user_id
     *
     * @mbggenerated
     */
    private Long userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_relation.add_time
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_relation.parents
     *
     * @mbggenerated
     */
    private String parents;

    public TRelation() {
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_relation
     *
     * @mbggenerated
     */
    public TRelation(Long userId, Date addTime, String parents) {
        this.userId = userId;
        this.addTime = addTime;
        this.parents = parents;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_relation.user_id
     *
     * @return the value of t_relation.user_id
     *
     * @mbggenerated
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_relation.add_time
     *
     * @return the value of t_relation.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_relation.parents
     *
     * @return the value of t_relation.parents
     *
     * @mbggenerated
     */
    public String getParents() {
        return parents;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public void setParents(String parents) {
        this.parents = parents;
    }
}