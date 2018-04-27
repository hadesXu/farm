package com.hades.farm.core.data.mapper;

import com.hades.farm.api.view.response.StealModel;
import com.hades.farm.core.data.dto.requestDto.*;
import com.hades.farm.core.data.entity.TDuckWarehouse;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface TDuckWarehouseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_duck_warehouse
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_duck_warehouse
     *
     * @mbggenerated
     */
    int insert(TDuckWarehouse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_duck_warehouse
     *
     * @mbggenerated
     */
    int insertSelective(TDuckWarehouse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_duck_warehouse
     *
     * @mbggenerated
     */
    TDuckWarehouse selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_duck_warehouse
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TDuckWarehouse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_duck_warehouse
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TDuckWarehouse record);

    TDuckWarehouse selectByUserId(long userId);

    int updateDuckWareHouseBreedingDuck(BreedingRequestDto requestDto);

    int updateDuckWareHouseBuyDuck(BuyGoodsRequestDto requestDto);

    int updateDuckWareHouseBuyFeed(BuyGoodsRequestDto requestDto);

    int updateDuckWareHouseSellEgg(PublishOrderRequestDto requestDto);

    int updateDuckWareHouseOfFeeding(UpdateDuckWareHouseFeedingRequestDto requestDto);

    int updateDuckWareHouseCumulativeData(WareHouseCumulativeDataRequestDto requestDto);

    /**
     * 偷蛋列表
     *
     * @param userId
     * @return
     */
    List<StealModel> queryCanStealList(@Param("userId") Long userId, @Param("offSet") int offSet, @Param("pageSize") int pageSize);

    /**
     * 被偷
     *
     * @param userId
     * @param stealNum
     * @return
     */
    int updateOfStealByOther(@Param("userId") Long userId, @Param("stealNum") int stealNum);

    /**
     * 偷别人的
     *
     * @param userId
     * @param stealNum
     * @return
     */
    int updateOfStealOther(@Param("userId") Long userId, @Param("stealNum") int stealNum);


    int duckChange(@Param("userId") long userId, @Param("num") BigDecimal num);
}