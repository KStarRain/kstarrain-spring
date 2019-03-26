package com.kstarrain.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kstarrain.vo.TestVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TestController {

    private final Logger log = LoggerFactory.getLogger(TestController.class);



    @RequestMapping(value = "/test1",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<JSON> test1(HttpServletRequest request) {
        String token = request.getHeader("token");
        JSONObject resultDTO = new JSONObject();
        resultDTO.put("token",token);
        resultDTO.put("message","/test1[GET] 请求成功");
        return ResponseEntity.ok().body(resultDTO);
    }


    @RequestMapping(value = "/test2",method = RequestMethod.POST )
    @ResponseBody
    public ResponseEntity<JSON> test2(@RequestBody TestVO testIn) {
//        produces = MediaType.APPLICATION_JSON_UTF8_VALUE"application/json;text/plain;charset=UTF-8"
        JSONObject resultDTO = new JSONObject();
        resultDTO.put("message","/test2[POST] 请求成功");
        return ResponseEntity.ok().body(resultDTO);
    }




    @RequestMapping(value = "/test3",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<JSON> test3(@RequestParam("name")String name) {

        log.info("request  : " + JSON.toJSONString(name));
        JSONObject resultDTO = new JSONObject();
        resultDTO.put("message","/test3[POST] 请求成功");
        resultDTO.put("name",name);
        return ResponseEntity.ok().body(resultDTO);
    }
}
