package com.hades.farm.core.service.impl;

import com.hades.farm.core.data.entity.TNotice;
import com.hades.farm.core.data.mapper.TNoticeMapper;
import com.hades.farm.core.service.NoticeService;
import com.hades.farm.result.Result;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xiaoxu on 2018/3/18.
 */
public class NoticeServiceImpl implements NoticeService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private TNoticeMapper tNoticeMapper;

    @Override
    public Result<List<TNotice>> getNotice(long userId) {
        Result<List<TNotice>> result = Result.newResult();
        List<TNotice> tNotices = tNoticeMapper.getNoticeByUserId(userId);
        if (CollectionUtils.isNotEmpty(tNotices)) {
            result.setData(tNotices);
        }
        return result;
    }
}
