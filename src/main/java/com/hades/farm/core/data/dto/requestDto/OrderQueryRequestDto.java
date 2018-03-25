package com.hades.farm.core.data.dto.requestDto;

/**
 * Created by zhengzl on 2018/3/25.
 */
public class OrderQueryRequestDto {
    private Long userId;
    private int offSet;
    private long pageSize;
    private Integer type;
    //是否排除自己的记录
    private Integer ifExceptSelf;//1:是，其它：否

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getOffSet() {
        return offSet;
    }

    public void setOffSet(int offSet) {
        this.offSet = offSet;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getIfExceptSelf() {
        return ifExceptSelf;
    }

    public void setIfExceptSelf(Integer ifExceptSelf) {
        this.ifExceptSelf = ifExceptSelf;
    }
}
