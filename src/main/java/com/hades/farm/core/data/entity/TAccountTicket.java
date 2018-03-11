package com.hades.farm.core.data.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TAccountTicket {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account_ticket.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account_ticket.user_id
     *
     * @mbggenerated
     */
    private Long userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account_ticket.balance
     *
     * @mbggenerated
     */
    private BigDecimal balance;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account_ticket.frozen
     *
     * @mbggenerated
     */
    private BigDecimal frozen;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account_ticket.acc_recharge
     *
     * @mbggenerated
     */
    private BigDecimal accRecharge;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account_ticket.acc_withdraw
     *
     * @mbggenerated
     */
    private BigDecimal accWithdraw;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account_ticket.acc_profit
     *
     * @mbggenerated
     */
    private BigDecimal accProfit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account_ticket.acc_commission
     *
     * @mbggenerated
     */
    private BigDecimal accCommission;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account_ticket.add_time
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account_ticket.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_account_ticket
     *
     * @mbggenerated
     */
    public TAccountTicket(Long id, Long userId, BigDecimal balance, BigDecimal frozen, BigDecimal accRecharge, BigDecimal accWithdraw, BigDecimal accProfit, BigDecimal accCommission, Date addTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
        this.frozen = frozen;
        this.accRecharge = accRecharge;
        this.accWithdraw = accWithdraw;
        this.accProfit = accProfit;
        this.accCommission = accCommission;
        this.addTime = addTime;
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_account_ticket.id
     *
     * @return the value of t_account_ticket.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_account_ticket.user_id
     *
     * @return the value of t_account_ticket.user_id
     *
     * @mbggenerated
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_account_ticket.balance
     *
     * @return the value of t_account_ticket.balance
     *
     * @mbggenerated
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_account_ticket.frozen
     *
     * @return the value of t_account_ticket.frozen
     *
     * @mbggenerated
     */
    public BigDecimal getFrozen() {
        return frozen;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_account_ticket.acc_recharge
     *
     * @return the value of t_account_ticket.acc_recharge
     *
     * @mbggenerated
     */
    public BigDecimal getAccRecharge() {
        return accRecharge;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_account_ticket.acc_withdraw
     *
     * @return the value of t_account_ticket.acc_withdraw
     *
     * @mbggenerated
     */
    public BigDecimal getAccWithdraw() {
        return accWithdraw;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_account_ticket.acc_profit
     *
     * @return the value of t_account_ticket.acc_profit
     *
     * @mbggenerated
     */
    public BigDecimal getAccProfit() {
        return accProfit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_account_ticket.acc_commission
     *
     * @return the value of t_account_ticket.acc_commission
     *
     * @mbggenerated
     */
    public BigDecimal getAccCommission() {
        return accCommission;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_account_ticket.add_time
     *
     * @return the value of t_account_ticket.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_account_ticket.update_time
     *
     * @return the value of t_account_ticket.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }
}