package com.hades.farm.core.service;

import com.hades.farm.result.Result;

/**
 * Created by xiaoxu on 2018/3/17.
 */
public interface RelationService {

    /**
     * 初始化用户关系记录
     *
     * @param userId
     * @param parentId
     */
    void initRelation(long userId, long parentId);

    /**
     * 获取用户关系
     *
     * @param userId
     * @return
     */
    Result<String> getRelation(long userId);

}
