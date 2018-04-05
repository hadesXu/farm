package com.hades.farm.api.view.response;

import com.hades.farm.core.data.entity.TDuckWarehouse;
import com.hades.farm.core.data.entity.TEggWarehouse;

/**
 * Created by zhengzl on 2018/4/5.
 */
public class MyWarehouseModel {
    private TDuckWarehouse duckWarehouse;
    private TEggWarehouse eggWarehouse;

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
}
