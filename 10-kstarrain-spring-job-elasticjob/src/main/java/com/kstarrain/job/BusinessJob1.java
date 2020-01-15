package com.kstarrain.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.kstarrain.service.ITestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: DongYu
 * @create: 2019-02-26 17:11
 * @description:
 */
@Slf4j
public class BusinessJob1 implements SimpleJob {

    @Autowired
    private ITestService testService;

    @Override
    public void execute(ShardingContext shardingContext) {

        try {
            testService.test1(shardingContext.getJobParameter());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

    }
}
