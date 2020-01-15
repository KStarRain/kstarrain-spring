package com.kstarrain.config;

import com.kstarrain.config.properties.RabbitMQProperties;
import com.kstarrain.constants.MQConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    public CachingConnectionFactory cachingConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(rabbitMQProperties.getAddresses());
        connectionFactory.setUsername(rabbitMQProperties.getUsername());
        connectionFactory.setPassword(rabbitMQProperties.getPassword());
        connectionFactory.setVirtualHost(rabbitMQProperties.getVirtualHost());
        connectionFactory.setPublisherConfirms(rabbitMQProperties.isPublisherConfirms());
        connectionFactory.setPublisherReturns(rabbitMQProperties.isPublisherReturns());
        return connectionFactory;
    }





    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        /**
         * 定制化amqp模版
         *
         * ConfirmCallback接口用于实现消息发送到RabbitMQ交换器后接收ack回调   即消息发送到exchange  ack
         * ReturnCallback接口用于实现消息发送到RabbitMQ 交换器，但无相应队列与交换器绑定时的回调  即消息发送不到任何一个队列中  ack
         */
        // 消息发送失败返回到队列中, yml需要配置 publisher-returns: true
        rabbitTemplate.setMandatory(true);

        /**
         * yml需要配置 publisher-confirms: true
         * 如果消息没有到exchange,则confirm回调,ack=false
         * 如果消息到达exchange,则confirm回调,ack=true
         */
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                log.error("消息发送至交换机失败,原因：{}", cause);
            }
        });


        /**
         * yml需要配置 publisher-returns: true
         * exchange到queue成功,则不回调return
         * exchange到queue失败,则回调return
         */
        rabbitTemplate.setReturnCallback(new RabbitMQReturnCallback(rabbitTemplate));



        return rabbitTemplate;
    }





    /**
     * 创建admin，为了能声明队列和交换机以及绑定关系
     */
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        TopicExchange topicExchange = new TopicExchange(MQConstants.MY_TOPIC_EXCHANGE);
        Queue myQueue1 = new Queue(MQConstants.MY_QUEUE1);
        Binding binding1 = BindingBuilder.bind(myQueue1).to(topicExchange).with(MQConstants.MY_ROUTING_KEY);

        rabbitAdmin.declareExchange(topicExchange);
        rabbitAdmin.declareQueue(myQueue1);
        rabbitAdmin.declareBinding(binding1);


        FanoutExchange fanoutExchange = new FanoutExchange(MQConstants.MY_FANOUT_EXCHANGE);
        Queue myQueue2 = new Queue(MQConstants.MY_QUEUE2);
        Binding binding2 = BindingBuilder.bind(myQueue2).to(fanoutExchange);

        rabbitAdmin.declareExchange(fanoutExchange);
        rabbitAdmin.declareQueue(myQueue2);
        rabbitAdmin.declareBinding(binding2);

        return rabbitAdmin;
    }


}
