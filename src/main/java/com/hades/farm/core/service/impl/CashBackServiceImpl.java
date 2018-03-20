package com.hades.farm.core.service.impl;

import com.hades.farm.core.exception.BizException;
import com.hades.farm.core.service.CashBackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by xiaoxu on 2018/3/18.
 */
@Service
public class CashBackServiceImpl implements CashBackService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cashBack(long userId) throws BizException {

        return false;
    }
}
