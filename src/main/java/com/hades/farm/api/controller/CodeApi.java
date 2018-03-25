package com.hades.farm.api.controller;

import com.hades.farm.api.view.ApiResponse;
import com.hades.farm.api.view.request.CodeRequest;
import com.hades.farm.core.service.CodeService;
import com.hades.farm.result.Result;
import com.hades.farm.utils.NetUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by xiaoxu on 2018/3/25.
 */
@RestController
@RequestMapping("/api")
public class CodeApi {
    @Resource
    private CodeService codeService;

    @RequestMapping(value = "/phone/code", method = RequestMethod.GET)
    public ApiResponse<Void> sendPhoneCode(CodeRequest request, HttpServletRequest servletRequest) {
        ApiResponse<Void> response = new ApiResponse<>();
        String ip = NetUtils.getRemortIP(servletRequest);
        Result<String> sendRes = codeService.getAndSendPhoneCode(request.getPhone(), request.getWechat(), ip);
        if (!sendRes.isSuccess()) {
            response.addError(sendRes.getErrorCodes());
            return response;
        }
        return response;
    }

    @RequestMapping(value = "/phone/retrieve/code", method = RequestMethod.GET)
    public ApiResponse<Void> retrieveCode(@RequestParam(required = false, defaultValue = "0") String phone
            , HttpServletRequest servletRequest) {
        ApiResponse<Void> response = new ApiResponse<>();
        String ip = NetUtils.getRemortIP(servletRequest);
        Result<String> sendRes = codeService.getAndSendPhoneCode(phone, ip);
        if (!sendRes.isSuccess()) {
            response.addError(sendRes.getErrorCodes());
            return response;
        }
        return response;
    }

    @RequestMapping(value = "/phone/retrieve/valid", method = RequestMethod.GET)
    public ApiResponse<Void> retrieveCode(@RequestParam(required = false, defaultValue = "0") String phone
            , @RequestParam(required = false, defaultValue = "0") String code) {
        ApiResponse<Void> response = new ApiResponse<>();
        Result<Void> sendRes = codeService.validPhoneCode(phone, code);
        if (!sendRes.isSuccess()) {
            response.addError(sendRes.getErrorCodes());
            return response;
        }
        return response;
    }
}
