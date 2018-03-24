package com.hades.farm.core.service.impl;

import com.hades.farm.core.data.entity.User;
import com.hades.farm.core.data.mapper.UserMapper;
import com.hades.farm.core.service.GradeService;
import com.hades.farm.enums.Grade;
import com.hades.farm.utils.Constant;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xiaoxu on 2018/3/15.
 */
@Service
public class GradeServiceImpl implements GradeService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private UserMapper userMapper;

    @Override
    public void updateGrade(long userId, Grade grade) {
        int uRes = userMapper.updateGrade(userId, grade.getType());
        if (uRes == Constant.NUMBER_ONE) {
            logger.info("update user grade error userId:{}", userId);
        }
        if (grade == Grade.ADVISER) {//升级师爷 触发父类 是否到底 地主阶级
            landlordUpgrade(userId);
        }
    }

    @Override
    @Async("commonExecutor")
    public void adviserUpgrade(long userId) {
        User apprenticeUser = userMapper.getUserById(userId);
        if (apprenticeUser != null && apprenticeUser.getParentId() > Constant.DEFAULT_ID) {
            User parentUser = userMapper.getUserById(apprenticeUser.getParentId());
            if (parentUser != null) {
                if (parentUser.getGrade() < Grade.ADVISER.getType()) {
                    if (getThreeGenerationsCount(parentUser.getId()) > Constant.ADVISER_CONDITION_NUMBER) {
                        updateGrade(parentUser.getId(), Grade.ADVISER);
                    }
                }
                parentUser = userMapper.getUserById(parentUser.getParentId());
                if (parentUser != null) {
                    if (parentUser.getGrade() < Grade.ADVISER.getType()) {
                        if (getThreeGenerationsCount(parentUser.getId()) > Constant.ADVISER_CONDITION_NUMBER) {
                            updateGrade(parentUser.getId(), Grade.ADVISER);
                        }
                    }
                    parentUser = userMapper.getUserById(parentUser.getParentId());
                    if (parentUser != null) {
                        if (parentUser.getGrade() < Grade.ADVISER.getType()) {
                            if (getThreeGenerationsCount(parentUser.getId()) > Constant.ADVISER_CONDITION_NUMBER) {
                                updateGrade(parentUser.getId(), Grade.ADVISER);
                            }
                        }
                    }
                }

            }
        }
    }

    @Override
    @Async("commonExecutor")
    public void landlordUpgrade(long userId) {
        User apprenticeUser = userMapper.getUserById(userId);
        if (apprenticeUser != null && apprenticeUser.getParentId() > Constant.DEFAULT_ID) {
            User parentUser = userMapper.getUserById(apprenticeUser.getParentId());
            if (parentUser != null && parentUser.getGrade() < Grade.LANDLORD.getType()) {
                List<User> apprentices = userMapper.getApprentice(userId);
                if (CollectionUtils.isEmpty(apprentices)) {
                    int count = Constant.NUMBER_ZERO;
                    for (User user : apprentices) {
                        if (user.getGrade() > Grade.MENTOR.getType()) {
                            count++;
                        }
                        if (count >= Constant.LANDLORD_CONDITION_NUMBER) {
                            updateGrade(parentUser.getId(), Grade.LANDLORD);
                            break;
                        }
                    }
                }
            }

        }
    }

    @Override
    public void partnerUpgrade(long userId) {

    }

    @Override
    public void partnerAgency(long userId) {

    }

    /**
     * 获取三代之内的人数
     *
     * @param userId
     * @return
     */
    private int getThreeGenerationsCount(long userId) {
        int count = Constant.NUMBER_ZERO;
        //所有徒弟
        List<User> apprentices = userMapper.getApprentice(userId);
        List<User> childApprentices = null;
        if (CollectionUtils.isEmpty(apprentices)) {
            count = apprentices.size();
            for (User user : apprentices) {
                childApprentices = userMapper.getApprentice(userId);
                if (CollectionUtils.isEmpty(childApprentices)) {
                    for (User ChildUser : childApprentices) {
                        count += userMapper.getApprenticeCount(ChildUser.getId());
                    }
                }
            }
            count += childApprentices.size();
        }
        return count;
    }
}
