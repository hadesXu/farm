package com.hades.farm.core.data.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TPayItem {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pay_item.item_id
     *
     * @mbggenerated
     */
    private Long itemId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pay_item.price
     *
     * @mbggenerated
     */
    private BigDecimal price;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pay_item.image
     *
     * @mbggenerated
     */
    private String image;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pay_item.status
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pay_item.remarks
     *
     * @mbggenerated
     */
    private String remarks;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pay_item.add_time
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pay_item
     *
     * @mbggenerated
     */
    public TPayItem(Long itemId, BigDecimal price, String image, Integer status, String remarks, Date addTime) {
        this.itemId = itemId;
        this.price = price;
        this.image = image;
        this.status = status;
        this.remarks = remarks;
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_pay_item.item_id
     *
     * @return the value of t_pay_item.item_id
     *
     * @mbggenerated
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_pay_item.price
     *
     * @return the value of t_pay_item.price
     *
     * @mbggenerated
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_pay_item.image
     *
     * @return the value of t_pay_item.image
     *
     * @mbggenerated
     */
    public String getImage() {
        return image;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_pay_item.status
     *
     * @return the value of t_pay_item.status
     *
     * @mbggenerated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_pay_item.remarks
     *
     * @return the value of t_pay_item.remarks
     *
     * @mbggenerated
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_pay_item.add_time
     *
     * @return the value of t_pay_item.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }
}