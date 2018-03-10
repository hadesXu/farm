package com.hades.farm.core.data.dto.requestDto;

import java.math.BigDecimal;

/**
 * Created by zhengzl on 2018/3/10.
 */
public class UpdateAccountTicketRequestDto {
    private long userId;
    private BigDecimal balance;
    private int acctOpreType;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getAcctOpreType() {
        return acctOpreType;
    }

    public void setAcctOpreType(int acctOpreType) {
        this.acctOpreType = acctOpreType;
    }

}
