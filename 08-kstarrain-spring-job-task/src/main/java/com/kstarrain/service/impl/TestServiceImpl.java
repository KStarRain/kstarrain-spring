package com.kstarrain.service.impl;

import com.kstarrain.service.ITestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestServiceImpl implements ITestService {


    public  void test1() throws InterruptedException {
        log.info(Thread.currentThread().getName()  + "开始执行作业逻辑task1");
            Thread.sleep(10000L);
//            int a = 5/0;
        log.info(Thread.currentThread().getName()  + "结束执行作业逻辑task1");
    }

    @Override
    public void test2() {
        log.info(Thread.currentThread().getName()  + "开始执行作业逻辑task2");
        log.info(Thread.currentThread().getName()  + "结束执行作业逻辑task2");
    }

}
