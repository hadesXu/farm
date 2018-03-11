package com.hades.farm.core.data.mapper;

import com.hades.farm.core.data.entity.TSmsRecord;

public interface TSmsRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sms_record
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sms_record
     *
     * @mbggenerated
     */
    int insert(TSmsRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sms_record
     *
     * @mbggenerated
     */
    int insertSelective(TSmsRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sms_record
     *
     * @mbggenerated
     */
    TSmsRecord selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sms_record
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TSmsRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sms_record
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TSmsRecord record);


    TSmsRecord getByPhone(String phone);
}