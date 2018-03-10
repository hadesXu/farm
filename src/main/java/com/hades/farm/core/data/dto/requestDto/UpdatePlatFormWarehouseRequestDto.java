package com.hades.farm.core.data.dto.requestDto;

/**
 * Created by zhengzl on 2018/3/10.
 */
public class UpdatePlatFormWarehouseRequestDto {
    private int updateEggNum;
    private int updateDuckNum;
    private int updateDogNum;

    public int getUpdateEggNum() {
        return updateEggNum;
    }

    public void setUpdateEggNum(int updateEggNum) {
        this.updateEggNum = updateEggNum;
    }

    public int getUpdateDuckNum() {
        return updateDuckNum;
    }

    public void setUpdateDuckNum(int updateDuckNum) {
        this.updateDuckNum = updateDuckNum;
    }

    public int getUpdateDogNum() {
        return updateDogNum;
    }

    public void setUpdateDogNum(int updateDogNum) {
        this.updateDogNum = updateDogNum;
    }
}
