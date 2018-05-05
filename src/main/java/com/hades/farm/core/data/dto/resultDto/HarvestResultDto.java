package com.hades.farm.core.data.dto.resultDto;

/**
 * Created by zhengzl on 2018/5/5.
 */
public class HarvestResultDto {
    private int produce;
    private int status;
    private int numFreeze;

    public int getProduce() {
        return produce;
    }

    public void setProduce(int produce) {
        this.produce = produce;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNumFreeze() {
        return numFreeze;
    }

    public void setNumFreeze(int numFreeze) {
        this.numFreeze = numFreeze;
    }
}
