package com.hades.farm.core.service;

import com.hades.farm.api.view.response.FdcInfoModel;
import com.hades.farm.api.view.response.MsgModel;
import com.hades.farm.api.view.response.YjcInfoModel;
import com.hades.farm.core.exception.BizException;

/**
 * Created by zhengzl on 2018/3/24.
 */
public interface FarmService {
    public YjcInfoModel queryYjcInfo(long userId) throws BizException;
    public FdcInfoModel queryFdcInfo(long userId) throws BizException;
    public String shouhuo(long userId,String goodTypeStr) throws BizException;
}
