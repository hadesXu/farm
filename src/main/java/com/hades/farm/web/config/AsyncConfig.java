package com.hades.farm.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by xiaoxu on 2018/3/15.
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    private String taskThreadNamePrefix = "taskTaskExecutor-";
    private String commonThreadNamePrefix = "commonTaskExecutor-";

    @Bean(name = "taskExecutor")
    public TaskExecutor taskExecutor() {
        return taskExecutor(30, 10,
                1, taskThreadNamePrefix);
    }

    @Bean(name = "commonExecutor")
    public TaskExecutor commonExecutor() {
        return taskExecutor(50, 15,
                1, commonThreadNamePrefix);
    }

    private TaskExecutor taskExecutor(int maxPoolSize, int corePoolSize,
                                      int queueCapacity, String namePrfix) {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(maxPoolSize);
        taskExecutor.setCorePoolSize(corePoolSize);
        taskExecutor.setQueueCapacity(queueCapacity);
        taskExecutor.setThreadNamePrefix(namePrfix);

        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.initialize();

        return taskExecutor;
    }

}
