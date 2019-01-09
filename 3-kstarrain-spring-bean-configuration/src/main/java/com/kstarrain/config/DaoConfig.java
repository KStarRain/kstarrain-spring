package com.kstarrain.config;

import com.kstarrain.dao.ITestDao;
import com.kstarrain.dao.impl.Test1DaoImpl;
import com.kstarrain.dao.impl.Test2DaoImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Dong Yu
 * @create: 2018-10-12 14:12
 * @description: 手动注入dao配置
 */

@Configuration
public class DaoConfig  implements ApplicationContextAware {

    @Bean
    ITestDao test1DaoImpl(){
        System.out.println("=======准备注入Test1DaoImpl=======");
        return new Test1DaoImpl();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        System.out.println("=======准备注入空参Test2DaoImpl=======");
        BeanDefinitionBuilder factory = BeanDefinitionBuilder.genericBeanDefinition("com.kstarrain.dao.impl.Test2DaoImpl");
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
        defaultListableBeanFactory.registerBeanDefinition("test2DaoImpl",factory.getBeanDefinition());



        ITestDao testDao = applicationContext.getBean("test2DaoImpl",ITestDao.class);
        testDao.test();


        System.out.println("=======准备注入有参Test2DaoImpl=======");
        BeanDefinitionBuilder factory2 = factory;
//                BeanDefinitionBuilder.rootBeanDefinition(Test2DaoImpl.class);
        factory2.addConstructorArgValue("haha");
        factory2.addConstructorArgValue("123");
        factory2.setInitMethodName("test");
        DefaultListableBeanFactory defaultListableBeanFactory2 = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
        defaultListableBeanFactory2.registerBeanDefinition("test2DaoImpl",factory2.getBeanDefinition());

        ITestDao testDao2 = applicationContext.getBean("test2DaoImpl",ITestDao.class);
        testDao2.test();

    }

}
