package com.hades.farm.api.view.response;

import com.hades.farm.core.data.dto.resultDto.NoticeUserResultDto;
import com.hades.farm.core.data.dto.resultDto.OrderUserResultDto;
import com.hades.farm.core.data.entity.TNotice;
import com.hades.farm.core.data.entity.TOrders;
import com.hades.farm.core.data.entity.TPlatformWarehouse;

import java.util.List;

/**
 * Created by zhengzl on 2018/3/25.
 */
public class OrderIndexModel {
    private List<OrderUserResultDto> allOrderList;
    private List<OrderUserResultDto> myOrderList;
    private List<NoticeUserResultDto> noticeList;
    private TPlatformWarehouse platformWarehouse;
    //可出售的商品鸭数量
    private int duckNum;
    //可出售的商品蛋数量
    private int eggNum;

    public List<OrderUserResultDto> getAllOrderList() {
        return allOrderList;
    }

    public void setAllOrderList(List<OrderUserResultDto> allOrderList) {
        this.allOrderList = allOrderList;
    }

    public List<OrderUserResultDto> getMyOrderList() {
        return myOrderList;
    }

    public void setMyOrderList(List<OrderUserResultDto> myOrderList) {
        this.myOrderList = myOrderList;
    }

    public List<NoticeUserResultDto> getNoticeList() {
        return noticeList;
    }

    public void setNoticeList(List<NoticeUserResultDto> noticeList) {
        this.noticeList = noticeList;
    }

    public TPlatformWarehouse getPlatformWarehouse() {
        return platformWarehouse;
    }

    public void setPlatformWarehouse(TPlatformWarehouse platformWarehouse) {
        this.platformWarehouse = platformWarehouse;
    }

    public int getDuckNum() {
        return duckNum;
    }

    public void setDuckNum(int duckNum) {
        this.duckNum = duckNum;
    }

    public int getEggNum() {
        return eggNum;
    }

    public void setEggNum(int eggNum) {
        this.eggNum = eggNum;
    }
}
