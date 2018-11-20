package com.kstarrain.controller;

import com.kstarrain.service.ITestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@Slf4j
public class IndexController {

    private final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    ITestService testService;

    @RequestMapping(value = "/index")
    public String index() {
        logger.info("index");
        return "index";
    }

    @RequestMapping(value = "/index2")
    public String index2() {
        logger.info("index2");
        return "index";
    }

}
