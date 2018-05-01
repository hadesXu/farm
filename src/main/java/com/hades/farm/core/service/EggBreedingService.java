package com.hades.farm.core.service;

import com.hades.farm.core.data.dto.requestDto.BreedingRequestDto;
import com.hades.farm.core.exception.BizException;

import java.util.List;
import java.util.Map;

/**
 * Created by zhengzl on 2018/3/9.
 */
public interface EggBreedingService {
      public boolean breeding(BreedingRequestDto requestDto) throws BizException;

      public boolean warmSelf(long userId) throws BizException;

      public boolean warmMaster(long userId) throws BizException;

      public List<Map> queryBreeList(long userId, int offSet, int pageSize);
}
