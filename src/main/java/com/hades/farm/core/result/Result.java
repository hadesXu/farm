package com.hades.farm.core.result;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.hades.farm.core.result.error.Error;
import com.hades.farm.core.result.error.ErrorCode;

import java.util.List;
import java.util.Set;

/**
 * service层的返回结果对象
 * Created by xiaoxu on 2018/3/4.
 */
public class Result<T> {

    /**
     * 服务是否成功，如果成功则为true，否则则为false
     */
    private boolean success = true;
    /**
     * 如果success为false，则包含错误提示信息， 否则为null
     */
    private List<Error> errors;
    /**
     * 错误key的set， 防止错误重复
     */
    private Set<String> keySet;

    /**
     * 调用service后返回的结果
     */
    private T object;


    /**
     * 创建result对象
     *
     * @param <T>
     * @return
     */
    public static <T> Result<T> newResult() {
        return new Result<T>();
    }

    /**
     * 创建错误result对象
     *
     * @param <T>
     * @return
     */
    public static <T> Result<T> newErrorResult(ErrorCode errorCode, T obj) {
        Result<T> result = new Result<T>();
        result.setSuccess(false);
        result.setObject(obj);
        result.addError(errorCode);
        return result;
    }

    /**
     * 返回出错信息
     *
     * @return
     */
    public String getErrMsg() {
        if (success) {
            throw new UnsupportedOperationException("Result is success");
        }

        List<String> tmpList = Lists.newArrayList();

        for (Error error : errors) {
            tmpList.add(error.getErrMsg());
        }
        Joiner joiner = Joiner.on(";").skipNulls();
        return joiner.join(tmpList);
    }

    /**
     * 新增一个错误
     *
     * @param code 错误编码
     * @param msg  错误信息
     */
    public void addError(String code, String msg) {
        if (keySet == null) {//此时表明无错误
            this.setSuccess(false);
            keySet = Sets.newHashSet();
            errors = Lists.newArrayList();
        }

        if (!keySet.contains(code)) {
            keySet.add(code);
            errors.add(new Error(code, msg));
        }
    }

    /**
     * 新增一个错误
     *
     * @param errorCode
     */
    public void addError(ErrorCode errorCode) {
        if (keySet == null) {//此时表明无错误
            this.setSuccess(false);
            keySet = Sets.newHashSet();
            errors = Lists.newArrayList();
        }

        if (!keySet.contains(errorCode.getErrCode())) {
            keySet.add(errorCode.getErrCode());
            errors.add(new Error(errorCode.getErrCode(), errorCode.getErrMsg()));
        }
    }

    /**
     * 增加errors
     *
     * @param errors
     */
    public void addErrors(List<Error> errors) {
        for (Error error : errors) {
            this.addError(error.getErrCode(), error.getErrMsg());
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public Set<String> getKeySet() {
        return keySet;
    }

    public void setKeySet(Set<String> keySet) {
        this.keySet = keySet;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

}
