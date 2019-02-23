package com.kstarrain.job;

import com.kstarrain.service.ITestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: DongYu
 * @create: 2019-02-22 11:03
 * @description:
 *
 *  1.默认单线程同步执行
 *  2.单个任务时，当前次的调度完成后，再执行下一次任务调度
 *  3.多个任务时，一个任务执行完成后才会执行下一个任务。
 *    多个任务时若需要任务能够并发执行，需手动设置线程池
 */
@Slf4j
@Component
public class TestJob {

    @Autowired
    ITestService testService;


    public void executeJob1() {
        try {
            testService.test1();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }



    public void executeJob2() {
        try {
            testService.test2();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }
}
