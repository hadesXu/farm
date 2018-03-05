package com.hades.farm.web.model;

/**
 * 返回给客户端的对象模型
 * Created by xiaoxu on 2018/3/4.
 */
public class ViewResult<T> {
    /**
     * 是否成功,默认为true
     */
    private boolean ok = true;
    /**
     * 是否需要重新登录,true需要重新登录
     */
    private boolean relogin;
    /**
     * 苹果支付回调是否重试 true-重试 false-不重试
     */
    private boolean applePayRetry = true;
    /**
     * 提示信息
     */
    private String tips;
    /**
     * 返回的结果对象
     */
    private T object;

    /**
     * 新增一个错误
     *
     * @param errorMsg
     */
    public void addError(String errorMsg) {
        this.setOk(false);
        this.setTips(errorMsg);
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public boolean isRelogin() {
        return relogin;
    }

    public void setRelogin(boolean relogin) {
        this.relogin = relogin;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public boolean isApplePayRetry() {
        return applePayRetry;
    }

    public void setApplePayRetry(boolean applePayRetry) {
        this.applePayRetry = applePayRetry;
    }
}
