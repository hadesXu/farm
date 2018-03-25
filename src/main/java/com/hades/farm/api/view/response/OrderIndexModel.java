package com.hades.farm.api.view.response;

import com.hades.farm.core.data.dto.resultDto.NoticeUserResultDto;
import com.hades.farm.core.data.dto.resultDto.OrderUserResultDto;
import com.hades.farm.core.data.entity.TNotice;
import com.hades.farm.core.data.entity.TOrders;

import java.util.List;

/**
 * Created by zhengzl on 2018/3/25.
 */
public class OrderIndexModel {
    private List<OrderUserResultDto> allOrderList;
    private List<OrderUserResultDto> myOrderList;
    private List<NoticeUserResultDto> noticeList;

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
}
