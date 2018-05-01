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

    int updateAuth(@Param("userId") long userId, @Param("auth") int auth);

    int updatePwd(@Param("userId") long userId, @Param("pwd") String pwd);

    int updateUser(@Param("userId") long userId, @Param("birth") Long birth, @Param("sex") Integer sex, @Param("auth") Integer auth);

    int updateNameAndImgUrl(@Param("userId") long userId, @Param("name") String name, @Param("imgUrl") String imgUrl);

    /**
     * 获取徒弟
     *
     * @param userId
     * @return
     */
    List<User> getApprentice(long userId);

    /**
     * 获取徒弟
     *
     * @param userId
     * @param offset
     * @param size
     * @return
     */
    List<User> getApprenticeOffset(@Param("userId") long userId, @Param("offset") int offset, @Param("size") int size);

    /**
     * 获取徒弟数量
     *
     * @param userId
     * @return
     */
    int getApprenticeCount(long userId);

    /**
     * 获取徒弟
     *
     * @param userId
     * @param offset
     * @param size
     * @return
     */
    List<User> getSon(@Param("userId") long userId, @Param("offset") int offset, @Param("size") int size);

    /**
     * 获取徒子的数量
     *
     * @param userId
     * @return
     */
    int getSonCount(long userId);

    /**
     * 获取徒孙
     *
     * @param userId
     * @param offset
     * @param size
     * @return
     */
    List<User> getDisciple(@Param("userId") long userId, @Param("offset") int offset, @Param("size") int size);

    /**
     * 获取徒孙数量
     *
     * @param userId
     * @return
     */
    int getDiscipleCount(long userId);

    int updateSduckEgg(User user);

}
