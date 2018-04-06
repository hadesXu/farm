package com.hades.farm.core.service.impl;

import com.hades.farm.core.data.entity.TAccountTicket;
import com.hades.farm.core.data.entity.TPayItem;
import com.hades.farm.core.data.entity.TRecharge;
import com.hades.farm.core.data.mapper.TPayItemMapper;
import com.hades.farm.core.data.mapper.TRechargeMapper;
import com.hades.farm.core.service.RechargeService;
import com.hades.farm.result.ErrorCode;
import com.hades.farm.result.Result;
import com.hades.farm.utils.Constant;
import com.hades.farm.utils.SystemUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by xiaoxu on 2018/4/6.
 */
@Service
public class RechargeServiceImpl implements RechargeService {
    @Autowired
    private TPayItemMapper tPayItemMapper;
    @Autowired
    private TRechargeMapper tRechargeMapper;

    @Override
    public Result<List<TPayItem>> getItem() {
        Result<List<TPayItem>> result = Result.newResult();
        List<TPayItem> items = tPayItemMapper.findAll();
        if (CollectionUtils.isNotEmpty(items)) {
            result.setData(items);
        }
        return result;
    }

    @Override
    public Result<Void> placeOrder(long userId, long itemId) {
        Result<Void> result = Result.newResult();
        TPayItem tPayItem = tPayItemMapper.selectByPrimaryKey(itemId);
        if (tPayItem == null) {
            result.addError(ErrorCode.ITEM_NOT_EXIST);
            return result;
        }
        TRecharge tRecharge = new TRecharge();
        tRecharge.setUserId(userId);
        tRecharge.setAmount(tPayItem.getPrice());
        tRecharge.setType(Constant.NUMBER_TWO + "");
        tRecharge.setStatus(Constant.NUMBER_ONE + "");
        tRecharge.setAddTime(new Date());
        tRecharge.setRechargeNo(String.valueOf(SystemUtil.generateOrderNum(userId)));
        int uRes = tRechargeMapper.insertSelective(tRecharge);
        if (uRes == Constant.NUMBER_ZERO) {
            result.addError(ErrorCode.ADD_ERR);
            return result;
        }
        return result;
    }
}
