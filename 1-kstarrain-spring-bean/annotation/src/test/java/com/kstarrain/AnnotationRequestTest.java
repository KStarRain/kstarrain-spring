package com.kstarrain;

import com.kstarrain.controller.Test1Controller;
import com.kstarrain.controller.Test2Controller;
import com.kstarrain.service.ITestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:config/application.xml"})
public class AnnotationRequestTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private Test1Controller test1Controller;

    @Autowired
    private Test2Controller test2Controller;

    @Autowired
    @Qualifier("test1ServiceImpl")
    ITestService testService;

    @Test
    public void sendRequestTest1() {
        test1Controller.test();
    }

    @Test
    public void sendRequestTest2() {
        System.out.println("---------------------------------------");
        test2Controller.test();
        System.out.println("---------------------------------------");
    }

}
