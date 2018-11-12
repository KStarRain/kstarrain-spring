package com.kstarrain;

import com.kstarrain.controller.TestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:config/application.xml"})
public class ConfigRequestTest {

    @Autowired
    private TestController testController;

    @Test
    public void sendRequestTest() {
        testController.test();
    }



}
