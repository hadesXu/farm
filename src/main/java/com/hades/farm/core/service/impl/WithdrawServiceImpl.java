package com.hades.farm.core.service.impl;

import com.hades.farm.api.view.request.WithdrawRequest;
import com.hades.farm.core.data.entity.*;
import com.hades.farm.core.data.mapper.*;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.core.service.CodeService;
import com.hades.farm.core.service.WithdrawService;
import com.hades.farm.enums.AcctOpreType;
import com.hades.farm.enums.WithdrawType;
import com.hades.farm.result.ErrorCode;
import com.hades.farm.result.Result;
import com.hades.farm.utils.Constant;
import com.hades.farm.utils.DateUtils;
import com.hades.farm.utils.SystemUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by xiaoxu on 2018/4/6.
 */
@Service
public class WithdrawServiceImpl implements WithdrawService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private TWithdrawMapper tWithdrawMapper;
    @Resource
    private TIdentityCardRecordMapper tIdentityCardRecordMapper;
    @Resource
    private TAccountTicketMapper tAccountTicketMapper;
    @Resource
    private TAccountTicketFlowMapper tAccountTicketFlowMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private CodeService codeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void withdraw(WithdrawRequest request) throws BizException {
        if (!DateUtils.isInTime(Constant.WITHDRAW_TIME, DateUtils.getDayHHMM(new Date()))) {
            throw new BizException(ErrorCode.WITHDRAW_ERROR);
        }
        if (!WithdrawType.valid(request.getType())) {
            throw new BizException(ErrorCode.ARGUMENTS_ERROR);
        }
        WithdrawType withdrawType = WithdrawType.getType(request.getType());
        if (withdrawType == WithdrawType.ALIPAY) {
            if (request.getAmount().compareTo(new BigDecimal(10000)) > Constant.NUMBER_ZERO) {
                throw new BizException(ErrorCode.ARGUMENTS_ERROR);
            }
        }
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= Constant.NUMBER_ZERO) {
            throw new BizException(ErrorCode.ARGUMENTS_ERROR);
        }
        TAccountTicket tAccountTicket = tAccountTicketMapper.queryAccountByUserId(request.getUserId());
        if (tAccountTicket.getBalance().compareTo(request.getAmount()) <= Constant.NUMBER_ZERO) {
            throw new BizException(ErrorCode.ARGUMENTS_ERROR);
        }

        User user = userMapper.getUserById(request.getUserId());
        if (user == null) {
            throw new BizException(ErrorCode.USER_NOT_EXIST);
        }
        if (user.getIsAuth() != Constant.NUMBER_TWO) {
            throw new BizException(ErrorCode.AUTH_ERROR);
        }
        Date startTime = DateUtils.getFirstDate();
        Date endTime = DateUtils.getLastDay();
        int count = tWithdrawMapper.withdrawCount(request.getUserId(), startTime, endTime);
        if (count >= Constant.NUMBER_ONE) {
            throw new BizException(ErrorCode.WITHDRAW_COUNT_ERROR);
        }
        Result<Void> voidResult = codeService.validPhoneCode(user.getTelephone(), request.getCode());
        if (!voidResult.isSuccess()) {
            throw new BizException(ErrorCode.ARGUMENTS_ERROR);
        }
        int uRes = tAccountTicketMapper.withdraw(request.getUserId(), request.getAmount());
        if (uRes == Constant.NUMBER_ZERO) {
            throw new BizException(ErrorCode.UPDATE_ERR);
        }
        //新增账户流水表记录
        TAccountTicketFlow accountTicketFlow = new TAccountTicketFlow();
        accountTicketFlow.setUserId(request.getUserId());
        accountTicketFlow.setType(AcctOpreType.WITHDRAW.getType());
        accountTicketFlow.setAmount(request.getAmount());
        accountTicketFlow.setAmountBefore(tAccountTicket.getBalance());
        accountTicketFlow.setAmountAfter(tAccountTicket.getBalance().subtract(request.getAmount()));
        accountTicketFlow.setRemarks("提现冻结：" + request.getAmount());
        accountTicketFlow.setAddTime(new Date());
        uRes = tAccountTicketFlowMapper.insertSelective(accountTicketFlow);
        if (uRes == Constant.NUMBER_ZERO) {
            throw new BizException(ErrorCode.ADD_ERR);
        }
        TWithdraw tWithdraw = new TWithdraw();
        tWithdraw.setWithdrawNo(String.valueOf(SystemUtil.generateOrderNum(request.getUserId())));
        tWithdraw.setType(String.valueOf(withdrawType.type));
        tWithdraw.setAmount(request.getAmount());
        tWithdraw.setStatus("1");
        tWithdraw.setAddTime(new Date());

        BigDecimal rate = BigDecimal.ZERO;
        if (withdrawType.type == 1) { //支付宝2%提现手续费
            rate = new BigDecimal("0.02");
        } else if (withdrawType.type == 2) { //银行卡5%提现手续费
            rate = new BigDecimal("0.05");
        }
        tWithdraw.setRate(rate);
        BigDecimal fee = request.getAmount().multiply(rate).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        tWithdraw.setFee(fee);

        BigDecimal interbank = request.getAmount().subtract(fee).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        tWithdraw.setInterbank(interbank);
        tWithdraw.setUserId(request.getUserId());
        TIdentityCardRecord record = tIdentityCardRecordMapper.getByUserId(request.getUserId());
        if (record != null) {
            tWithdraw.setRealName(record.getRealName());

        }
        if (withdrawType == WithdrawType.ALIPAY) {
            tWithdraw.setAlipayAccount(request.getAlipayAccount());
        } else {
            tWithdraw.setCardNo(request.getCardNo());
            tWithdraw.setBankName(request.getBankName());
            tWithdraw.setBranchName(request.getBranchName());

        }
        uRes = tWithdrawMapper.insert(tWithdraw);
        if (uRes == Constant.NUMBER_ZERO) {
            throw new BizException(ErrorCode.ADD_ERR);
        }
    }
}
