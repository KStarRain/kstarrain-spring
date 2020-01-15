package com.kstarrain.config;

import com.kstarrain.constants.MQConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: DongYu
 * @create: 2020-01-09 16:01
 * @description:
 */
@Configuration
public class RabbitMQExchangeConfig {

    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange(MQConstants.ROUTING_KEY);
    }

    @Bean
    public Queue myQueue() {
        return new Queue(MQConstants.MY_QUEUE);
    }


    @Bean
    public Binding binding() {
        return BindingBuilder.bind(this.myQueue()).to(this.exchange());
    }

}
