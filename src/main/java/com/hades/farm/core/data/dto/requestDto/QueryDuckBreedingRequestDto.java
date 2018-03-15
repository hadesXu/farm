package com.hades.farm.core.data.dto.requestDto;

/**
 * Created by zhengzl on 2018/3/15.
 */
public class QueryDuckBreedingRequestDto {
    private long userId;
    private int status;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
