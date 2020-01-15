package com.kstarrain.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author: DongYu
 * @create: 2019-02-19 09:07
 * @description: RabbitMQ配置信息
 *
 */
@Component
@PropertySource("classpath:properties/rabbit.properties")
@Data
public class RabbitMQProperties {

    @Value("${spring.rabbitmq.addresses}")
    private String addresses;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    @Value("${spring.rabbitmq.publisher-confirms}")
    private boolean publisherConfirms;

    @Value("${spring.rabbitmq.publisher-returns}")
    private boolean publisherReturns;

}
