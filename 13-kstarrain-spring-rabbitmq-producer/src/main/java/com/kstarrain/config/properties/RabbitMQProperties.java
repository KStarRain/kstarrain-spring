package com.kstarrain.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author: DongYu
 * @create: 2019-02-19 09:07
 * @description: redis配置信息
 *
 *  1. 在类名上面使用  @PropertySource("classpath:*") 注解,*代表属性文件路径,可以指向多个配置文件路径，如果是多个配置文件,则是 @PropertySource({"classpath:*","classpath:*"....})
 *  2. 在字段上直接使用@value注解，注解内使用${core.pool.size}  core.pool.size 代表属性文件里面的key

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
