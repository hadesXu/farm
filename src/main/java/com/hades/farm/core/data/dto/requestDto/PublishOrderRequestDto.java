package com.hades.farm.core.data.dto.requestDto;

/**
 * Created by zhengzl on 2018/3/12.
 */
public class PublishOrderRequestDto {
    private int type;
    private int num;
    private long userId;

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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
