package com.hades.farm.core.service.impl;

import com.hades.farm.api.view.response.YjcInfoModel;
import com.hades.farm.core.exception.BizException;

/**
 * Created by zhengzl on 2018/3/24.
 */
public interface FarmService {
    public YjcInfoModel queryYjcInfo(long userId) throws BizException;
}
