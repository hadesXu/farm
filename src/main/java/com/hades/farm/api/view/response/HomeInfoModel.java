package com.hades.farm.api.view.response;

import java.math.BigDecimal;

/**
 * Created by xiaoxu on 2018/4/5.
 */
public class HomeInfoModel {

    /**
     * 账户余额
     */
    private BigDecimal balance = BigDecimal.ZERO;

    /**
     * 账户积分
     */
    private BigDecimal integral = BigDecimal.ZERO;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getIntegral() {
        return integral;
    }

    public void setIntegral(BigDecimal integral) {
        this.integral = integral;
    }
}
