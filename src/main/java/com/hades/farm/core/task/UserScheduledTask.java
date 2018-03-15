package com.hades.farm.core.task;

import com.hades.farm.core.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by xiaoxu on 2018/3/15.
 */
@Component
public class UserScheduledTask {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private UserService userService;

    @Scheduled(cron = "0 30 10 * * * ")
    public void recycle() {
        logger.info("test start");
        logger.info("test end");
    }

}
