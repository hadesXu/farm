package com.hades.farm.web.handler;

import com.hades.farm.api.view.ApiResponse;
import com.hades.farm.result.ErrorCode;
import com.langu.authorization.exception.TokenInvalidException;
import com.langu.authorization.exception.TokenJsonpInvalidException;
import com.langu.authorization.exception.TokenNullException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xiaoxu on 2018/4/5.
 */
@ControllerAdvice
public class DefaultExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public ApiResponse handleConflict(Exception ex, HttpServletRequest request) {
        logger.error("Default error handler, url:{}", request.getRequestURI(), ex);
        ApiResponse response = new ApiResponse();
        response.addError(ErrorCode.SYSTEM_ERROR);
        return response;
    }


    @ExceptionHandler(value = {TokenInvalidException.class, TokenNullException.class})
    @ResponseBody
    public ApiResponse handleTokenExpired(Exception ex, HttpServletRequest request) {
        logger.info("Token expired error handler, userId:{},  token:{}, url:{}", request.getParameter("userId"),
                request.getParameter("x-token"), request.getRequestURI());
        ApiResponse response = new ApiResponse();
        response.addError(ErrorCode.NO_LOGIN);
        return response;
    }

    @ExceptionHandler(value = {TokenJsonpInvalidException.class})
    @ResponseBody
    public ApiResponse handleJsonpTokenExpired(Exception ex, HttpServletRequest request) {
        logger.error("JsonP token expired error handle, userId:{},  token:{},url:{}", request.getParameter("userId"),
                request.getParameter("x-token"), request.getRequestURI(), ex);
        String callback = request.getParameter("callbackparam");
        ApiResponse response = new ApiResponse();
        response.addError(ErrorCode.NO_LOGIN);
        return response;
    }
}
