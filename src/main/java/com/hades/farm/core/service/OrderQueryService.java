package com.hades.farm.core.service;

import com.hades.farm.api.view.response.OrderIndexModel;
import com.hades.farm.core.data.dto.requestDto.OrderQueryRequestDto;
import com.hades.farm.core.data.dto.resultDto.OrderUserResultDto;
import com.hades.farm.core.data.entity.TOrders;

import java.util.List;

/**
 * Created by zhengzl on 2018/3/25.
 */
public interface OrderQueryService {
    public List<OrderUserResultDto> queryOrderList(OrderQueryRequestDto requestDto);

    public OrderIndexModel orderIndex(long userId);
}
