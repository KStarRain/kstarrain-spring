package com.kstarrain;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: Dong Yu
 * @create: 2018-12-18 09:05
 * @description:
 */
public class SpringBeanRegisterUtil {

    private static ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    private static ConfigurableApplicationContext configurableContext = (ConfigurableApplicationContext) context;
    private static BeanDefinitionRegistry beanDefinitionRegistry = (DefaultListableBeanFactory) configurableContext.getBeanFactory();


}
