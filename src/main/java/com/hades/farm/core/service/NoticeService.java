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
    Result<List<TNotice>> getNotice(long userId, int page, int num);

    /**
     * 获取养殖记录
     *
     * @param userId
     * @param page
     * @param num
     * @return
     */
    Result<List<TNotice>> getBreedNotice(long userId, int page, int num);

    public List<TNotice> getNumNotice(long userId, int num);

}
