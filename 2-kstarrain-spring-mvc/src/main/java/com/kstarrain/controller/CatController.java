package com.kstarrain.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class CatController {

    @RequestMapping(value = "/cat")
    public String cat() {
        log.debug("cat debug");
        log.info("cat info");
        return "cat";
    }



}
