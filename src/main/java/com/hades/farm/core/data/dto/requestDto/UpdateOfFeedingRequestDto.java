package com.hades.farm.core.data.dto.requestDto;

/**
 * Created by zhengzl on 2018/3/15.
 */
public class UpdateOfFeedingRequestDto {
    private long userId;
    private Long[] ids;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }
}
