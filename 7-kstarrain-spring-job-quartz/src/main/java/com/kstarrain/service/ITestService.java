package com.kstarrain.service;

import org.quartz.JobDataMap;

public interface ITestService {

    void test1(JobDataMap jobDataMap) throws InterruptedException;

    void test2() throws InterruptedException;
}
