package com.hades.farm.api.controller;

import com.hades.farm.api.convert.AccountConverter;
import com.hades.farm.api.view.ApiResponse;
import com.hades.farm.core.data.entity.*;
import com.hades.farm.core.service.AccountService;
import com.hades.farm.result.Result;
import com.langu.authorization.annotation.Auth;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xiaoxu on 2018/4/5.
 */
@RestController
@RequestMapping("/api/account")
public class AccountApi {
    @Resource
    private AccountService accountService;
    @Resource
    private AccountConverter accountConverter;


    @RequestMapping(value = "/integral/record", method = RequestMethod.GET)
    @Auth
    public ApiResponse getBreedNotice(@RequestParam long userId,
                                      @RequestParam(required = false, defaultValue = "1") int page,
                                      @RequestParam(required = false, defaultValue = "20") int num) {
        ApiResponse response = new ApiResponse<>();
        Result<List<TAccountIntegralFlow>> result = accountService.findIntegralRecord(userId, page, num);
        if (!result.isSuccess()) {
            response.addError(result.getErrorCodes());
            return response;
        }
        response.setResult(accountConverter.converterIntegral(result.getData()));
        return response;
    }

    @RequestMapping(value = "/ticket/record", method = RequestMethod.GET)
    @Auth
    public ApiResponse findTicketRecord(@RequestParam long userId,
                                        @RequestParam(required = false, defaultValue = "1") int page,
                                        @RequestParam(required = false, defaultValue = "20") int num) {
        ApiResponse response = new ApiResponse<>();
        Result<List<TAccountTicketFlow>> result = accountService.findTicketRecord(userId, page, num);
        if (!result.isSuccess()) {
            response.addError(result.getErrorCodes());
            return response;
        }
        response.setResult(accountConverter.converterTicket(result.getData()));
        return response;
    }


    @RequestMapping(value = "/record", method = RequestMethod.GET)
    @Auth
    public ApiResponse findAccountRecord(@RequestParam long userId,
                                         @RequestParam(required = false, defaultValue = "1") int page,
                                         @RequestParam(required = false, defaultValue = "20") int num) {
        ApiResponse response = new ApiResponse<>();
        Result<List<TAccountTicketFlow>> result = accountService.findAccountRecord(userId, page, num);
        if (!result.isSuccess()) {
            response.addError(result.getErrorCodes());
            return response;
        }
        response.setResult(accountConverter.converter(result.getData()));
        return response;
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @Auth
    public ApiResponse getAccount(@RequestParam long userId) {
        ApiResponse response = new ApiResponse<>();
        Result<TAccountTicket> result = accountService.getAccount(userId);
        if (!result.isSuccess()) {
            response.addError(result.getErrorCodes());
            return response;
        }
        response.setResult(result.getData());
        return response;
    }

    @RequestMapping(value = "/integral/info", method = RequestMethod.GET)
    @Auth
    public ApiResponse getAccountIntegral(@RequestParam long userId) {
        ApiResponse response = new ApiResponse<>();
        Result<TAccountIntegral> result = accountService.getAccountIntegral(userId);
        if (!result.isSuccess()) {
            response.addError(result.getErrorCodes());
            return response;
        }
        response.setResult(result.getData());
        return response;
    }

    @RequestMapping(value = "/withdraw/record", method = RequestMethod.GET)
    @Auth
    public ApiResponse findTWithdraw(@RequestParam long userId,
                                     @RequestParam(required = false, defaultValue = "1") int page,
                                     @RequestParam(required = false, defaultValue = "20") int num) {
        ApiResponse response = new ApiResponse<>();
        Result<List<TWithdraw>> result = accountService.findTWithdraw(userId, page, num);
        if (!result.isSuccess()) {
            response.addError(result.getErrorCodes());
            return response;
        }
        response.setResult(accountConverter.converterWithdraw(result.getData()));
        return response;
    }


}
