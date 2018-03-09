package com.hades.farm.core.data.dto.requestDto;

/**
 * Created by zhengzl on 2018/3/9.
 */
public class BreedingRequestDto {
    private long userId;
    private int num;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
