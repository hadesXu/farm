package com.hades.farm.api.convert;

import com.hades.farm.api.view.response.DealRecordModel;
import com.hades.farm.api.view.response.RecordModel;
import com.hades.farm.core.data.entity.TAccountIntegralFlow;
import com.hades.farm.core.data.entity.TAccountTicketFlow;

import java.util.List;

/**
 * Created by xiaoxu on 2018/4/6.
 */
public interface AccountConverter {

    /**
     * 转换积分记录模型
     *
     * @param tAccountIntegralFlows
     * @return
     */
    List<RecordModel> converterIntegral(List<TAccountIntegralFlow> tAccountIntegralFlows);

    /**
     * 转换佣金记录模型
     *
     * @param tAccountTicketFlows
     * @return
     */
    List<RecordModel> converterTicket(List<TAccountTicketFlow> tAccountTicketFlows);

    /**
     * 转换交易模型记录
     *
     * @param tAccountTicketFlows
     * @return
     */
    List<DealRecordModel> converter(List<TAccountTicketFlow> tAccountTicketFlows);
}
