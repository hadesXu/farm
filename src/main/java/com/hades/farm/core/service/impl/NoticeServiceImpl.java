package com.hades.farm.core.service.impl;

import com.hades.farm.core.data.entity.TNotice;
import com.hades.farm.core.data.mapper.TNoticeMapper;
import com.hades.farm.core.service.NoticeService;
import com.hades.farm.result.Result;
import com.hades.farm.utils.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xiaoxu on 2018/3/18.
 */
@Service
public class NoticeServiceImpl implements NoticeService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private TNoticeMapper tNoticeMapper;

    @Override
    public Result<List<TNotice>> getNotice(long userId, int page, int num) {
        Result<List<TNotice>> result = Result.newResult();
        int offset = (page - 1) * num;
        List<TNotice> tNotices = tNoticeMapper.findNoticeByUserId(userId, offset, num);
        if (CollectionUtils.isNotEmpty(tNotices)) {
            result.setData(tNotices);
        }
        return result;
    }

    @Override
    public Result<List<TNotice>> getBreedNotice(long userId, int page, int num) {
        Result<List<TNotice>> result = Result.newResult();
        int offset = (page - 1) * num;
        List<TNotice> tNotices = tNoticeMapper.findBreedNotice(userId, offset, num);
        if (CollectionUtils.isNotEmpty(tNotices)) {
            result.setData(tNotices);
        }
        return result;
    }

    @Override
    public List<TNotice> getNumNotice(long userId, int num) {
        List<TNotice> tNotices = tNoticeMapper.getNoticeByUserIdOfnum(userId, num);
        return tNotices;
    }
}
