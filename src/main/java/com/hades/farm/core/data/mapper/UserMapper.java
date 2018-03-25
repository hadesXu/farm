package com.hades.farm.core.data.mapper;


import com.hades.farm.core.data.dto.requestDto.BuyGoodsRequestDto;
import com.hades.farm.core.data.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by xiaoxu on 2018/3/4.
 */
public interface UserMapper {

    int insert(User user);

    User getUserById(long userId);

    User getUserByWeChat(String weChat);

    User getUserPhone(String telephone);

    int updateDogEndDay(BuyGoodsRequestDto requestDto);

    int updateRobotEndDay(BuyGoodsRequestDto requestDto);

    int updateGrade(@Param("userId") long userId, @Param("grade") int grade);

    int updatePwd(@Param("userId") long userId, @Param("pwd") String pwd);

    /**
     * 获取徒弟
     *
     * @param userId
     * @return
     */
    List<User> getApprentice(long userId);

    /**
     * 获取徒弟数量
     *
     * @param userId
     * @return
     */
    int getApprenticeCount(long userId);
}
