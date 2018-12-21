package com.kstarrain.controller;

import com.kstarrain.service.ITestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//@Slf4j
public class TestController {

    private final Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    ITestService testService;

    @RequestMapping(value = "test",method = RequestMethod.GET)
    @ResponseBody
    public String test() {
        log.debug("index debug");
        log.info("index info");

        System.out.println("执行 controller 方法");
        return testService.test();
    }

}
