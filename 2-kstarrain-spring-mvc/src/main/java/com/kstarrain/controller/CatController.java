package com.kstarrain.controller;

import com.kstarrain.service.ITestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@Slf4j
public class CatController {

    private final Logger logger = LoggerFactory.getLogger(CatController.class);

    @Autowired
    ITestService testService;

    @RequestMapping(value = "/cat")
    public String cat() {
        logger.debug("cat debug");
        logger.info("cat info");
        return "cat";
    }



}
