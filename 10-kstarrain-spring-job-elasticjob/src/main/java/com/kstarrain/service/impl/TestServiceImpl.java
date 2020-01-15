package com.kstarrain.service.impl;

import com.kstarrain.service.ITestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestServiceImpl implements ITestService {


    public  void test1(String jobParameter) throws InterruptedException {
        log.info("开始执行作业逻辑task1");
        System.out.println(jobParameter);
//        Thread.sleep(10000L);
        log.info("结束执行作业逻辑task1");
    }

    @Override
    public void test2(String jobParameter) throws InterruptedException {
        log.info("开始执行作业逻辑task2");
        System.out.println(jobParameter);
        log.info("结束执行作业逻辑task2");
    }

}
