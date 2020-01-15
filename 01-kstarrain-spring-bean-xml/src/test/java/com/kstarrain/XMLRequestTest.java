package com.kstarrain;

import com.kstarrain.controller.TestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:config/application.xml"})
public class XMLRequestTest extends AbstractJUnit4SpringContextTests {

    @Test
    public void sendRequest() {

        TestController testController = super.applicationContext.getBean("testController", TestController.class);

        testController.test();

    }
}
