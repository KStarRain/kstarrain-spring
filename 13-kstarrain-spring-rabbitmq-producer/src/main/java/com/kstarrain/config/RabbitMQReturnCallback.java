package com.kstarrain.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author: DongYu
 * @create: 2020-01-10 09:24
 * @description:
 */
@Slf4j
@Component
public class RabbitMQReturnCallback implements RabbitTemplate.ReturnCallback {

    private RabbitTemplate rabbitTemplate;

    public RabbitMQReturnCallback(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.error("消息：{} 发送至队列失败, 应答码：{} 交换机: {}, 路由键: {}, 原因：{}", new String(message.getBody()), replyCode, exchange, routingKey, replyText);
        log.error("开始重发");
        rabbitTemplate.convertAndSend(exchange, routingKey,  message);
    }
}
