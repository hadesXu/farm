package com.hades.farm.core.service;

import com.hades.farm.core.data.entity.TNotice;
import com.hades.farm.result.Result;

import java.util.List;

/**
 * Created by xiaoxu on 2018/3/18.
 */
public interface NoticeService {

    /**
     * 获取用户通知
     *
     * @param userId
     * @return
     */
    Result<List<TNotice>> getNotice(long userId);

    public List<TNotice> getNumNotice(long userId,int num);

}
