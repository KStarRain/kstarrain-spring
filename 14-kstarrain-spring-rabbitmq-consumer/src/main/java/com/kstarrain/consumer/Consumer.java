package com.kstarrain.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Slf4j
@Component
//@RabbitListener(queues = MQConstants.MY_QUEUE)
public class Consumer {

//    @RabbitHandler
    public void handleMessage(@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel, String message) throws IOException {

        try {
            /**
             * 关闭自动ack确认机制，改为手动更可靠 （的false，是将自动确认机制关闭）
             * deliveryTag: 该消息的index
             * multiple： 是否批量.true:将一次性ack所有小于deliveryTag的消息。
             */
            channel.basicAck(deliveryTag,false);
            log.info("消费者接收到消息[{}]", message);
        } catch (IOException e) {
            /**
             * deliveryTag:该消息的index
             * multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息。
             * requeue：被拒绝的是否重新入队列
             */
            channel.basicNack(deliveryTag,false,true);
        }
    }




}
