package com.hades.farm.core.data.mapper;

import com.hades.farm.core.data.entity.TRecharge;

public interface TRechargeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recharge
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recharge
     *
     * @mbggenerated
     */
    int insert(TRecharge record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recharge
     *
     * @mbggenerated
     */
    int insertSelective(TRecharge record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recharge
     *
     * @mbggenerated
     */
    TRecharge selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recharge
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TRecharge record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recharge
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TRecharge record);
}