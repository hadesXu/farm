package com.hades.farm.core.data.dto.requestDto;

import java.math.BigDecimal;

/**
 * Created by zhengzl on 2018/3/15.
 */
public class UpdateDuckWareHouseFeedingRequestDto {
    private long userId;
    private BigDecimal feedNum;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public BigDecimal getFeedNum() {
        return feedNum;
    }

    public void setFeedNum(BigDecimal feedNum) {
        this.feedNum = feedNum;
    }
}
