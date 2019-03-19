package com.kstarrain.controller;

import com.alibaba.fastjson.JSON;
import com.kstarrain.service.ITestService;
import com.kstarrain.vo.TestVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class TestController {

    private final Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    ITestService testService;

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    @ResponseBody
    public String test() {
        System.out.println("执行 controller 方法");
        String str = testService.test();
        return str;
    }


    @RequestMapping(value = "/test2",method = RequestMethod.POST, produces = "application/json;text/plain;charset=UTF-8")
    @ResponseBody
    public TestVO test2(@RequestBody TestVO testIn) {

        log.info("request  : " + JSON.toJSONString(testIn));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (testIn.getTime() != null){
            String time = sdf.format(testIn.getTime());
            System.out.println(time);
        }



        TestVO testVO = new TestVO();
        testVO.setName("董宇");
        testVO.setAge(28);
        testVO.setMoney(new BigDecimal(13.14).setScale(2,BigDecimal.ROUND_HALF_UP));
        testVO.setTime(new Date());

        List<String> interest = new ArrayList<>();
        interest.add("吃");
        interest.add("喝");
        interest.add("玩");
        interest.add("乐");
        testVO.setInterest(interest);

        return testVO;
    }


    @RequestMapping(value = "/test3",method = RequestMethod.POST, produces = "application/json;text/plain;charset=UTF-8")
    @ResponseBody
    public TestVO test3(@RequestBody @Valid TestVO testIn, BindingResult bindingResult) {

        log.info("request  : " + JSON.toJSONString(testIn));

        if (bindingResult != null && bindingResult.hasErrors()) {
            List<FieldError> allErrors = bindingResult.getFieldErrors();
            if (CollectionUtils.isNotEmpty(allErrors)) {

                List<String> msgs = new ArrayList<String>();
                for (FieldError err : allErrors) {
                    msgs.add("[" + err.getField() + "]-" + err.getDefaultMessage());
                }
                System.out.println(msgs);
            }
        }

        return new TestVO();
    }
}
