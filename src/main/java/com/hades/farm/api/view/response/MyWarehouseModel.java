package com.hades.farm.api.view.response;

import com.hades.farm.core.data.entity.TDuckWarehouse;
import com.hades.farm.core.data.entity.TEggWarehouse;

/**
 * Created by zhengzl on 2018/4/5.
 */
public class MyWarehouseModel {
    private TDuckWarehouse duckWarehouse;
    private TEggWarehouse eggWarehouse;
    private int duckFreezeNum;
    private int duckHasBreedNum;
    private int eggFreezeNum;
    private int eggHasBreedNum;

    public TDuckWarehouse getDuckWarehouse() {
        return duckWarehouse;
    }

    public void setDuckWarehouse(TDuckWarehouse duckWarehouse) {
        this.duckWarehouse = duckWarehouse;
    }

    public TEggWarehouse getEggWarehouse() {
        return eggWarehouse;
    }

    public void setEggWarehouse(TEggWarehouse eggWarehouse) {
        this.eggWarehouse = eggWarehouse;
    }

    public int getDuckFreezeNum() {
        return duckFreezeNum;
    }

    public void setDuckFreezeNum(int duckFreezeNum) {
        this.duckFreezeNum = duckFreezeNum;
    }

    public int getDuckHasBreedNum() {
        return duckHasBreedNum;
    }

    public void setDuckHasBreedNum(int duckHasBreedNum) {
        this.duckHasBreedNum = duckHasBreedNum;
    }

    public int getEggFreezeNum() {
        return eggFreezeNum;
    }

    public void setEggFreezeNum(int eggFreezeNum) {
        this.eggFreezeNum = eggFreezeNum;
    }

    public int getEggHasBreedNum() {
        return eggHasBreedNum;
    }

    public void setEggHasBreedNum(int eggHasBreedNum) {
        this.eggHasBreedNum = eggHasBreedNum;
    }
}
