package com.kstarrain.controller;

import com.kstarrain.service.ITestService;
import org.springframework.stereotype.Controller;

@Controller
public class TestController {

    ITestService testService;

    public void setTestService(ITestService testService) {
        this.testService = testService;
    }

    public void test() {
        System.out.println("执行controller方法");
        testService.test();
    }

}
