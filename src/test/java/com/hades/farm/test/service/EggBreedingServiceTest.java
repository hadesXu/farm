package com.hades.farm.test.service;

import com.hades.farm.core.data.dto.requestDto.BreedingRequestDto;
import com.hades.farm.core.exception.BizException;
import com.hades.farm.core.service.DuckBreedingService;
import com.hades.farm.core.service.EggBreedingService;
import com.hades.farm.test.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by zhengzl on 2018/3/10.
 */
public class EggBreedingServiceTest extends BaseTest {
    @Resource
    EggBreedingService eggBreedingService;

    @Resource
    DuckBreedingService duckBreedingService;

    @Test
    public void eggBreedingTest() throws BizException{
        BreedingRequestDto requestDto = new BreedingRequestDto();
        requestDto.setUserId(1);
        requestDto.setNum(1);
        try {
            boolean ifSuccess = eggBreedingService.breeding(requestDto);
        }catch (BizException e){
            System.out.println("errorCode:" +e.getErrCode()+"errorMsg:"+e.getErrMessage());
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void duckBreedingTest() throws BizException{
        BreedingRequestDto requestDto = new BreedingRequestDto();
        requestDto.setUserId(1);
        requestDto.setNum(1);
        try {
            boolean ifSuccess = duckBreedingService.breeding(requestDto);
        }catch (BizException e){
            System.out.println("errorCode:" +e.getErrCode()+"errorMsg:"+e.getErrMessage());
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
