package com.kstarrain.controller;

import com.kstarrain.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping
public class TestController {

    @Autowired
    ITestService testService;

    @RequestMapping(value = "test",method = RequestMethod.POST)
    @ResponseBody
    public String test() {
        System.out.println("执行 controller 方法");
        return testService.test();
    }

}
