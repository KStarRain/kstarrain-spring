package com.kstarrain.controller;

import com.kstarrain.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @Autowired
    ITestService testService;

    @RequestMapping(value = "/index")
    public String test() {
        System.out.println("index   ");
        return "index";
    }

}
