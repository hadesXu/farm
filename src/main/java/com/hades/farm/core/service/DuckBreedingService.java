package com.hades.farm.core.service;

import com.hades.farm.core.data.dto.requestDto.BreedingRequestDto;
import com.hades.farm.core.exception.BizException;

/**
 * Created by zhengzl on 2018/3/10.
 */
public interface DuckBreedingService {
    public boolean breeding(BreedingRequestDto requestDto) throws BizException;
}
