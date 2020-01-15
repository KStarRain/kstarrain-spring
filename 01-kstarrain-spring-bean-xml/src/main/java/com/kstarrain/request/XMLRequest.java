package com.kstarrain.request;

import com.kstarrain.controller.TestController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class XMLRequest{

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:com/kstarrain/config/application.xml");
        TestController testController = applicationContext.getBean("testController", TestController.class);

        testController.test();
    }

}
