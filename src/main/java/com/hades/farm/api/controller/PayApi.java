package com.hades.farm.api.controller;

import com.hades.farm.api.view.ApiResponse;
import com.hades.farm.api.view.request.WithdrawRequest;
import com.hades.farm.core.data.entity.TPayItem;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.core.service.RechargeService;
import com.hades.farm.core.service.WithdrawService;
import com.hades.farm.result.Result;
import com.langu.authorization.annotation.Auth;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xiaoxu on 2018/4/6.
 */
@RestController
@RequestMapping("/api/pay")
public class PayApi {
    @Resource
    private RechargeService rechargeService;
    @Resource
    private WithdrawService withdrawService;

    @RequestMapping(value = "/item", method = RequestMethod.GET)
    @Auth
    public ApiResponse getItem() {
        ApiResponse response = new ApiResponse<>();
        Result<List<TPayItem>> result = rechargeService.getItem();
        if (!result.isSuccess()) {
            response.addError(result.getErrorCodes());
            return response;
        }
        response.setResult(result.getData());
        return response;
    }

    @RequestMapping(value = "/place", method = RequestMethod.GET)
    @Auth
    public ApiResponse getItem(@RequestParam long userId,
                               @RequestParam(required = false, defaultValue = "0") int itemId) {
        ApiResponse response = new ApiResponse<>();
        Result<Void> result = rechargeService.placeOrder(userId, itemId);
        if (!result.isSuccess()) {
            response.addError(result.getErrorCodes());
            return response;
        }
        return response;
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.GET)
    @Auth
    public ApiResponse getItem(WithdrawRequest request) {
        ApiResponse response = new ApiResponse<>();
        try {
            withdrawService.withdraw(request);
        } catch (BizException e) {
            e.printStackTrace();
        }
        return response;
    }
}
