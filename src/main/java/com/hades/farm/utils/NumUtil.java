package com.hades.farm.utils;

import com.hades.farm.api.view.*;
import com.hades.farm.api.view.Error;
import com.hades.farm.result.ErrorCode;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhengzl on 2018/3/24.
 */
public class NumUtil {
    public static final String TWO_DECIMAL_REG = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$";

    public static final String INTEGER_REG="^[1-9]+[0-9]*$";
    /**
     * 校验两位小数
     * @param str
     * @return
     */
    public static ErrorCode validateTwoDecimal(String str){
        Pattern pattern= Pattern.compile(TWO_DECIMAL_REG);
        Matcher match=pattern.matcher(str);
        if(match.matches()==false){
            return ErrorCode.NUM_ILLEGAL;
        }
        return ErrorCode.SUCCESS;
    }

    /**
     * 校验整数
     * @param str
     * @return
     */
    public static ErrorCode validateInteger(String str){
        Pattern pattern= Pattern.compile(INTEGER_REG);
        Matcher match=pattern.matcher(str);
        if(match.matches()==false){
            return ErrorCode.NUM_ILLEGAL;
        }
        return ErrorCode.SUCCESS;
    }
}
