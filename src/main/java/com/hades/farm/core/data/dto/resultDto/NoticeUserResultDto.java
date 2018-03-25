package com.hades.farm.core.data.dto.resultDto;

/**
 * Created by zhengzl on 2018/3/25.
 */
public class NoticeUserResultDto {
    private long userId;
    private String name;
    private String remarks;
    private int type;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
