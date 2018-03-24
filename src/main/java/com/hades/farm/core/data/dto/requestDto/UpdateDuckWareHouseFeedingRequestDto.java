package com.hades.farm.core.data.dto.requestDto;

import java.math.BigDecimal;

/**
 * Created by zhengzl on 2018/3/15.
 */
public class UpdateDuckWareHouseFeedingRequestDto {
    private long userId;
    private int feedNum;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getFeedNum() {
        return feedNum;
    }

    public void setFeedNum(int feedNum) {
        this.feedNum = feedNum;
    }
}
