package com.hades.farm.core.data.dto.requestDto;

import java.math.BigDecimal;

/**
 * Created by zhengzl on 2018/3/19.
 */
public class WareHouseCumulativeDataRequestDto {
    private long userId;
    private int allSell;
    private BigDecimal allProfit;
    private BigDecimal allIntegral;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getAllSell() {
        return allSell;
    }

    public void setAllSell(int allSell) {
        this.allSell = allSell;
    }

    public BigDecimal getAllProfit() {
        return allProfit;
    }

    public void setAllProfit(BigDecimal allProfit) {
        this.allProfit = allProfit;
    }

    public BigDecimal getAllIntegral() {
        return allIntegral;
    }

    public void setAllIntegral(BigDecimal allIntegral) {
        this.allIntegral = allIntegral;
    }
}
