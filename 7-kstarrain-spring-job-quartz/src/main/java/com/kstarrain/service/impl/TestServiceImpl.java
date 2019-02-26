package com.kstarrain.service.impl;

import com.alibaba.fastjson.JSON;
import com.kstarrain.service.ITestService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestServiceImpl implements ITestService {


    public  void test1(JobDataMap jobDataMap) throws InterruptedException {
        log.info("开始执行作业逻辑task1");
        System.out.println(JSON.toJSONString(jobDataMap));
        Thread.sleep(10000L);
        log.info("结束执行作业逻辑task1");
    }

    @Override
    public void test2() throws InterruptedException {
        log.info("开始执行作业逻辑task2");
        Thread.sleep(10000L);
        log.info("结束执行作业逻辑task2");
    }

}
