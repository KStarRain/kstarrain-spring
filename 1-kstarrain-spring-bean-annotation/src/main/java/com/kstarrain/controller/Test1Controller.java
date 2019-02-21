package com.kstarrain.controller;

import com.kstarrain.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class Test1Controller {

    @Autowired
    @Qualifier("test1ServiceImpl")
    ITestService testService;

    public  void test() {
        System.out.println("---------------------------------------");
        System.out.println("执行 controller_01 方法");
        testService.test();
    }

}
