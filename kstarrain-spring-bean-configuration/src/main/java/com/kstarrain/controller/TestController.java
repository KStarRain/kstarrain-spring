package com.kstarrain.controller;

import com.kstarrain.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TestController {

    @Autowired
    ITestService testService;

    public  void test() {
        System.out.println("执行 controller 方法");
        testService.test();
    }

}
