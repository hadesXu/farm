package com.hades.farm.core.data.mapper;

import com.hades.farm.core.data.dto.requestDto.UpdateAccountTicketRequestDto;
import com.hades.farm.core.data.entity.TAccountTicket;

public interface TAccountTicketMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_account_ticket
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_account_ticket
     *
     * @mbggenerated
     */
    int insert(TAccountTicket record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_account_ticket
     *
     * @mbggenerated
     */
    int insertSelective(TAccountTicket record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_account_ticket
     *
     * @mbggenerated
     */
    TAccountTicket selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_account_ticket
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TAccountTicket record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_account_ticket
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TAccountTicket record);

    int updateAccountTicket(UpdateAccountTicketRequestDto requestDto);

    TAccountTicket queryAccountByUserId(long userId);
}