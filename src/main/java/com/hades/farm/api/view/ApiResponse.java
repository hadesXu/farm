package com.hades.farm.api.view;

import com.hades.farm.result.ErrorCode;
import com.hades.farm.utils.JSONUtils;
import org.apache.commons.collections.CollectionUtils;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by xiaoxu on 2018/3/9.
 */
public class ApiResponse<D> {
    /**
     * 执行结果是否成功
     */
    private byte status = 1;

    /**
     * 错误信息, 返回一个错误
     */
    private Error error;

    /**
     * 信息提示
     */
    private String tips;

    /**
     * 数据， 可以为空
     */
    private D result;

    /**
     * 设置错误信息
     *
     * @param codes
     */
    public void addError(Set<Integer> codes) {
        if (CollectionUtils.isEmpty(codes)) {
            throw new IllegalArgumentException("codes is empty");
        }

        /**
         * 只选取其中，作为错误返回
         */
        ErrorCode errorCode = null;
        Iterator<Integer> it = codes.iterator();
        while (it.hasNext()) {
            int code = it.next();
            errorCode = ErrorCode.get(code);
            if (errorCode != null) {
                break;
            }
        }

        if (errorCode == null) {
            throw new IllegalArgumentException("code is invalid, codes:" + JSONUtils.objectToStringNonEx(codes));
        }

        addError(errorCode);
    }

    /**
     * 设置错误信息
     *
     * @param errorCode
     */
    public void addError(ErrorCode errorCode) {
        this.setStatus((byte) 0);
        this.error = new Error();
        this.error.setCode(errorCode.getCode());
        this.error.setMessage(errorCode.getMessage());
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public D getResult() {
        return result;
    }

    public void setResult(D result) {
        this.result = result;
    }
}
