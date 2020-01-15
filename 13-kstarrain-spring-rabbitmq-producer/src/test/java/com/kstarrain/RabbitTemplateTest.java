package com.kstarrain;

import com.kstarrain.constants.MQConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CountDownLatch;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:config/application.xml"})
@Slf4j
public class RabbitTemplateTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * https://blog.csdn.net/dh554112075/article/details/90137869
     */
    @Test
    public void testSend(){
        try {
            System.out.println("=====================================================================");
            String message = "key_string";

            log.info("插入数据1");
//            rabbitTemplate.convertAndSend("123",  "123",  message);
//            rabbitTemplate.convertAndSend(MQConstants.MY_TOPIC_EXCHANGE,  "123",  message);
            rabbitTemplate.convertAndSend(MQConstants.MY_TOPIC_EXCHANGE,  MQConstants.MY_ROUTING_KEY,  message);
//            rabbitTemplate.convertAndSend(MQConstants.MY_FANOUT_EXCHANGE,  null,  message);
            log.info("插入数据2");

            /**
             *  测试类中如果不加下面代码，会报错 clean channel shutdown;，但实际上消息已发送成功
             */
            CountDownLatch countDownLatch = new CountDownLatch(1);
            countDownLatch.await();

            System.out.println("=====================================================================");
        } catch (Exception e) {
            log.error("回滚",e);
        }
    }



}
