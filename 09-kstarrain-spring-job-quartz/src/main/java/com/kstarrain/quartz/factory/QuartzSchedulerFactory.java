package com.kstarrain.quartz.factory;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * @author: DongYu
 * @create: 2019-02-25 15:11
 * @description:
 */
@Component
public class QuartzSchedulerFactory extends AdaptableJobFactory {


    // 需要使用这个Spring的自动装载Factory对Qurartz创建好jobDetail实例进行后续处理
    @Autowired
    private AutowireCapableBeanFactory capableBeanFactory;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {

        // 首先，调用父类的方法创建Quartz所需的JobDetail实例
        Object jobDetailInstance = super.createJobInstance(bundle);
        // 然后，使用Spring的自动装载Factory为创建好的JobDetail实例进行属性自动装配并将其纳入到Spring容器的管理之中
        capableBeanFactory.autowireBean(jobDetailInstance);
        return jobDetailInstance;
    }
}
