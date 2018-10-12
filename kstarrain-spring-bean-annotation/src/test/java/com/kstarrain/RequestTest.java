package com.kstarrain;

import com.kstarrain.controller.Test1Controller;
import com.kstarrain.controller.Test2Controller;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:config/applicationContext.xml"})
public class RequestTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private Test1Controller test1Controller;

    @Autowired
    private Test2Controller test2Controller;


    @Test
    public void sendRequestTest1() {
        test1Controller.test();
    }

    @Test
    public void sendRequestTest2() {

        test2Controller.test();
    }

}
