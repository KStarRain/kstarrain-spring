package com.kstarrain.autoconfig;

import com.kstarrain.dao.impl.Test2DaoImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Dong Yu
 * @create: 2018-10-12 14:12
 * @description: 手动注入dao配置
 */

@Configuration
public class TestDaoConfig implements ApplicationContextAware {

    //不手动定义bean的名字它会默认根据方法名字命名该bean
//    @Bean(name = "test2DaoImpl")
//    Test2DaoImpl test2DaoImpl(){
//        System.out.println("=======准备注入Test2DaoImpl=======");
//        return new Test2DaoImpl();
//    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        System.out.println("=======准备向spring容器中注册无参Test2DaoImpl()=======");
        BeanDefinitionBuilder factory = BeanDefinitionBuilder.genericBeanDefinition("com.kstarrain.dao.impl.Test2DaoImpl");
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
        defaultListableBeanFactory.registerBeanDefinition("test2DaoImpl",factory.getBeanDefinition());



//        ITestDao testDao = applicationContext.getBean("test2DaoImpl",ITestDao.class);
//        testDao.test();


        System.out.println("=======准备向spring容器中注册有参Test2DaoImpl(貂蝉,20)=======");
        BeanDefinitionBuilder factory2 = BeanDefinitionBuilder.rootBeanDefinition(Test2DaoImpl.class);
        factory2.addConstructorArgValue("貂蝉");
        factory2.addConstructorArgValue(20);
        //注册到spring容器时设置初始化方法(调用了一次)
//        factory2.setInitMethodName("test");
        DefaultListableBeanFactory defaultListableBeanFactory2 = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
        defaultListableBeanFactory2.registerBeanDefinition("test2DaoImpl",factory2.getBeanDefinition());

//        ITestDao testDao2 = applicationContext.getBean("test2DaoImpl",ITestDao.class);
//        testDao2.test();
        System.out.println("=======注册结束=======");
    }

}
