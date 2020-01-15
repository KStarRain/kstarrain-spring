package com.kstarrain.config;

import com.kstarrain.config.properties.RabbitMQProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author: DongYu
 * @create: 2019-02-19 09:02
 * @description: Spring整合rabbitMQ配置
 */
@Slf4j
@Configuration
public class RabbitMQConfig {

    @Autowired
    private RabbitMQProperties rabbitMQProperties;


    /** rabbitMQ连接工厂 rabbitMQ和spring的整合 */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(rabbitMQProperties.getAddresses());
        connectionFactory.setUsername(rabbitMQProperties.getUsername());
        connectionFactory.setPassword(rabbitMQProperties.getPassword());
        connectionFactory.setVirtualHost(rabbitMQProperties.getVirtualHost());
        connectionFactory.setPublisherConfirms(rabbitMQProperties.isPublisherConfirms());
        connectionFactory.setPublisherReturns(rabbitMQProperties.isPublisherReturns());
        return connectionFactory;
    }



    /**
     * 消息监听
     * @param connectionFactory
     * @return
     */
    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        //SimpleRabbitListenerContainerFactory发现消息中有content_type有text就会默认将其转换成string类型的
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }


    /**
     * 定义admin,producer中的exchange,queue会自动的利用该admin自动在spring中生成
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }


}
