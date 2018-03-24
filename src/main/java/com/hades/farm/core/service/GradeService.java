package com.hades.farm.core.service;

import com.hades.farm.enums.Grade;

/**
 * Created by xiaoxu on 2018/3/15.
 */
public interface GradeService {

    /**
     * 更新用户等级
     *
     * @param userId
     * @param grade
     */
    void updateGrade(long userId, Grade grade);


    /**
     * 师爷升级
     *
     * @param userId
     */
    void adviserUpgrade(long userId);


    /**
     * 地主升级
     *
     * @param userId
     */
    void landlordUpgrade(long userId);

    /**
     * 合伙人升级
     *
     * @param userId
     */
    void partnerUpgrade(long userId);

    /**
     * 代理人升级
     *
     * @param userId
     */
    void partnerAgency(long userId);


}
