package com.hades.farm.result;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

public class Result<D> {
    /**
     * true, 执行成功
     */
    private boolean success = true;

    /**
     * 承载的数据
     */
    private D data;

    /**
     * 错误码
     */
    private Set<Integer> errorCodes;

    /**
     * 错误value提示信息， 否则为null
     */
    private List<String> errorMsg;

    /**
     * 成功提示
     */
    private String successMsg;

    /**
     * 创建result对象
     *
     * @param <T>
     * @return
     */
    public static <T> Result<T> newResult() {
        return new Result<T>();
    }

    public void addError(ErrorCode code) {
        if (code == null) {
            throw new IllegalArgumentException("code cannot be null");
        }
        addError(code.getCode());
    }

    public void addErrors(Set<Integer> codes) {
        for (Integer code : codes) {
            addError(code);
        }
    }

    private void addError(int codeNum) {
        if (errorCodes == null) {
            this.setSuccess(false);
            errorCodes = Sets.newHashSet();
            errorMsg = Lists.newArrayList();
        }

        if (!errorCodes.contains(codeNum)) {
            errorCodes.add(codeNum);
            errorMsg.add(ErrorCode.get(codeNum).getMessage());
        }
    }

    public String getErrMsg() {
        if (success) {
            throw new UnsupportedOperationException("Result is success");
        }
        Joiner joiner = Joiner.on(";").skipNulls();
        return joiner.join(errorMsg);
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    public Set<Integer> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(Set<Integer> errorCodes) {
        this.errorCodes = errorCodes;
    }

    public List<String> getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(List<String> errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }
}
