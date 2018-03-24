package com.hades.farm.core.data.dto.requestDto;

import java.math.BigDecimal;

/**
 * Created by zhengzl on 2018/3/10.
 */
public class BuyGoodsRequestDto {
    private long userId;
    private int type;
    private int num;
    private int feedNum;
    private long orderId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getFeedNum() {
        return feedNum;
    }

    public void setFeedNum(int feedNum) {
        this.feedNum = feedNum;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}
