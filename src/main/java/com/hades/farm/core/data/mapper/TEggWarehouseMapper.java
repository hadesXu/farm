package com.hades.farm.core.data.mapper;

import com.hades.farm.api.view.response.StealModel;
import com.hades.farm.core.data.dto.requestDto.BreedingRequestDto;
import com.hades.farm.core.data.dto.requestDto.BuyGoodsRequestDto;
import com.hades.farm.core.data.dto.requestDto.PublishOrderRequestDto;
import com.hades.farm.core.data.dto.requestDto.WareHouseCumulativeDataRequestDto;
import com.hades.farm.core.data.entity.TEggWarehouse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TEggWarehouseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_egg_warehouse
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_egg_warehouse
     *
     * @mbggenerated
     */
    int insert(TEggWarehouse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_egg_warehouse
     *
     * @mbggenerated
     */
    int insertSelective(TEggWarehouse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_egg_warehouse
     *
     * @mbggenerated
     */
    TEggWarehouse selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_egg_warehouse
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TEggWarehouse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_egg_warehouse
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TEggWarehouse record);

    TEggWarehouse selectByUserId(Long userId);

    int updateEggWareHouseBreedingEgg(BreedingRequestDto requestDto);

    int updateEggWareHouseBuyDuck(BuyGoodsRequestDto requestDto);

    int updateEggWareHouseSellDuck(PublishOrderRequestDto requestDto);

    int updateEggWareHouseCumulativeData(WareHouseCumulativeDataRequestDto requestDto);

    List<StealModel> queryCanStealList(@Param("userId") Long userId,@Param("offSet")int offSet,@Param("pageSize")int pageSize);

    /**
     * 被偷
     * @param userId
     * @param stealNum
     * @return
     */
    int updateOfStealByOther(@Param("userId") Long userId,@Param("stealNum") int stealNum);

    /**
     * 偷别人的
     * @param userId
     * @param stealNum
     * @return
     */
    int updateOfStealOther(@Param("userId") Long userId,@Param("stealNum") int stealNum);

    int shouhuoDuck(Long userId);
}