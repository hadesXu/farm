package com.hades.farm.core.data.mapper;

import com.hades.farm.core.data.dto.requestDto.QueryDuckBreedingRequestDto;
import com.hades.farm.core.data.dto.requestDto.UpdateDuckWareHouseFeedingRequestDto;
import com.hades.farm.core.data.dto.requestDto.UpdateOfFeedingRequestDto;
import com.hades.farm.core.data.dto.resultDto.HarvestResultDto;
import com.hades.farm.core.data.entity.TDuckBreeding;

import java.util.List;
import java.util.Map;

public interface TDuckBreedingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_duck_breeding
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_duck_breeding
     *
     * @mbggenerated
     */
    int insert(TDuckBreeding record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_duck_breeding
     *
     * @mbggenerated
     */
    int insertSelective(TDuckBreeding record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_duck_breeding
     *
     * @mbggenerated
     */
    TDuckBreeding selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_duck_breeding
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TDuckBreeding record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_duck_breeding
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TDuckBreeding record);

    List<TDuckBreeding> queryDuckBreeding(QueryDuckBreedingRequestDto requestDto);

    int updateOfFeeding(UpdateOfFeedingRequestDto requestDto);

    Map queryNOFeed(Long user_id);

    Map queryHaveBreed(Map map);

    int queryCanStealNum(long userId);

    int updateOfStealByOther(long userId);

    List<HarvestResultDto> queryCanHarvestList(long userId);

    int harvestOfDoing(long userId);

    int harvestOfDone(long userId);

    int queryHarvestFreeze(long userId);

    int queryHarvestNum(long userId);

}