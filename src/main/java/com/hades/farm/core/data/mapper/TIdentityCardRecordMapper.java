package com.hades.farm.core.data.mapper;

import com.hades.farm.core.data.entity.TIdentityCardRecord;

public interface TIdentityCardRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_identity_card_record
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_identity_card_record
     *
     * @mbggenerated
     */
    int insert(TIdentityCardRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_identity_card_record
     *
     * @mbggenerated
     */
    int insertSelective(TIdentityCardRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_identity_card_record
     *
     * @mbggenerated
     */
    TIdentityCardRecord selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_identity_card_record
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TIdentityCardRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_identity_card_record
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TIdentityCardRecord record);


    TIdentityCardRecord getByUserId(Long userId);

    TIdentityCardRecord getByIdNo(String idNo);
}