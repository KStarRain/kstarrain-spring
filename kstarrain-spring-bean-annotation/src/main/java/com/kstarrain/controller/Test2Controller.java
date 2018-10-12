package com.kstarrain.controller;

import com.kstarrain.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class Test2Controller {

    ITestService testService;

    @Autowired
    public Test2Controller(@Qualifier("test2ServiceImpl")ITestService testService) {
        this.testService = testService;
    }


    public void test() {
        System.out.println("---------------------------------------");
        System.out.println("执行 controller_02 方法");
        testService.test();
    }

}
