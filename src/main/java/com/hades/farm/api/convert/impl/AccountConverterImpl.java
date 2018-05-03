package com.hades.farm.api.convert.impl;

import com.hades.farm.api.convert.AccountConverter;
import com.hades.farm.api.view.response.DealRecordModel;
import com.hades.farm.api.view.response.RecordModel;
import com.hades.farm.api.view.response.WithdrawRecordModel;
import com.hades.farm.core.data.entity.TAccountIntegralFlow;
import com.hades.farm.core.data.entity.TAccountTicketFlow;
import com.hades.farm.core.data.entity.TWithdraw;
import com.hades.farm.enums.IntegralType;
import com.hades.farm.enums.WithdrawStatus;
import com.hades.farm.enums.WithdrawType;
import com.hades.farm.utils.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoxu on 2018/4/6.
 */
@Component
public class AccountConverterImpl implements AccountConverter {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public List<RecordModel> converterIntegral(List<TAccountIntegralFlow> tAccountIntegralFlows) {
        if (CollectionUtils.isEmpty(tAccountIntegralFlows)) return null;
        List<RecordModel> models = new ArrayList<>();
        RecordModel model = null;
        for (TAccountIntegralFlow tAccountIntegralFlow : tAccountIntegralFlows) {
            model = new RecordModel();
            model.setAddTime(DateUtils.getDate(tAccountIntegralFlow.getAddTime()));
            model.setTypeStr(IntegralType.getType(Integer.parseInt(tAccountIntegralFlow.getType())).getDesc());
            model.setValue(tAccountIntegralFlow.getAmountAfter().subtract(tAccountIntegralFlow.getAmountBefore()));
            models.add(model);
        }
        return models;
    }

    @Override
    public List<RecordModel> converterTicket(List<TAccountTicketFlow> tAccountTicketFlows) {
        if (CollectionUtils.isEmpty(tAccountTicketFlows)) return null;
        List<RecordModel> models = new ArrayList<>();
        RecordModel model = null;
        for (TAccountTicketFlow tAccountTicketFlow : tAccountTicketFlows) {
            model = new RecordModel();
            model.setAddTime(DateUtils.getDate(tAccountTicketFlow.getAddTime()));
//            model.setValue(tAccountTicketFlow.getAmountAfter().subtract(tAccountTicketFlow.getAmountBefore()));
            model.setValue(tAccountTicketFlow.getAmount());
            model.setTypeStr(tAccountTicketFlow.getRemarks());
            models.add(model);
        }
        return models;
    }

    @Override
    public List<DealRecordModel> converter(List<TAccountTicketFlow> tAccountTicketFlows) {
        if (CollectionUtils.isEmpty(tAccountTicketFlows)) return null;
        List<DealRecordModel> models = new ArrayList<>();
        DealRecordModel model = null;
        BigDecimal bigDecimal = BigDecimal.ZERO;
        for (TAccountTicketFlow tAccountTicketFlow : tAccountTicketFlows) {
            model = new DealRecordModel();
            model.setAddTime(DateUtils.getDate(tAccountTicketFlow.getAddTime()));
            model.setDesc(tAccountTicketFlow.getRemarks());
            bigDecimal = tAccountTicketFlow.getAmountAfter().subtract(tAccountTicketFlow.getAmountBefore());
            model.setTypeStr(bigDecimal.compareTo(BigDecimal.ZERO) >= 0 ? "增加" : "扣除");
            model.setValueStr(bigDecimal.toString());
            models.add(model);
        }
        return models;
    }

    @Override
    public List<WithdrawRecordModel> converterWithdraw(List<TWithdraw> tWithdraws) {
        if (CollectionUtils.isEmpty(tWithdraws)) return null;
        List<WithdrawRecordModel> models = new ArrayList<>();
        WithdrawRecordModel model = null;
        for (TWithdraw tWithdraw : tWithdraws) {
            model = new WithdrawRecordModel();
            model.setAddTime(DateUtils.getDate(tWithdraw.getAddTime()));
            model.setValue(tWithdraw.getAmount());
            model.setStateStr(WithdrawStatus.getStatus(tWithdraw.getStatus()).getDesc());
            model.setTypeStr(WithdrawType.getType(Integer.parseInt(tWithdraw.getType())).tip);
            model.setNo(WithdrawType.getType(Integer.parseInt(tWithdraw.getType())) == WithdrawType.ALIPAY ? tWithdraw.getAlipayAccount() : tWithdraw.getCardNo());
            model.setRealName(tWithdraw.getRealName());
            models.add(model);
        }
        return models;
    }
}
