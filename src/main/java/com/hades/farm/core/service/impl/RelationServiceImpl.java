package com.hades.farm.core.service.impl;

import com.hades.farm.core.data.entity.TRelation;
import com.hades.farm.core.data.entity.User;
import com.hades.farm.core.data.mapper.TOrdersMapper;
import com.hades.farm.core.data.mapper.TRelationMapper;
import com.hades.farm.core.data.mapper.UserMapper;
import com.hades.farm.core.service.RelationService;
import com.hades.farm.result.ErrorCode;
import com.hades.farm.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by xiaoxu on 2018/3/17.
 */
public class RelationServiceImpl implements RelationService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private UserMapper userMapper;
    @Resource
    private TRelationMapper tRelationMapper;

    @Override
    @Async("commonExecutor")
    public void initRelation(long userId, long parentId) {
        User user = userMapper.getUserById(parentId);
        if (user != null) {
            TRelation relation = tRelationMapper.selectByPrimaryKey(parentId);
            TRelation userRelation = new TRelation();
            userRelation.setUserId(userId);
            if (relation == null) {
                userRelation.setParents(String.valueOf(parentId));
            } else {
                userRelation.setParents(relation.getParents() + "," + String.valueOf(parentId));
            }
            userRelation.setAddTime(new Date());
            tRelationMapper.insert(userRelation);
        }
    }

    @Override
    public Result<String> getRelation(long userId) {
        Result<String> result = Result.newResult();
        TRelation relation = tRelationMapper.selectByPrimaryKey(userId);
        if (relation == null) {
            result.addError(ErrorCode.RELATION_NOT_EXIST);
            return result;
        }
        result.setData(relation.getParents());
        return result;
    }
}
