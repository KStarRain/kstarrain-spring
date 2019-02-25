package com.kstarrain.quartz.config;


import com.alibaba.fastjson.JSON;
import com.kstarrain.quartz.annotation.QuartzScheduled;
import com.kstarrain.quartz.factory.QuartzSchedulerFactory;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * quartz配置类
 */
@Component
@Configuration
@Slf4j
public class QuartzConf implements ApplicationContextAware {

	private String prefix = "quartz.";
	private Environment environment;

    private QuartzSchedulerFactory quartzSchedulerFactory;


	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.environment = applicationContext.getEnvironment();
        this.quartzSchedulerFactory = applicationContext.getBean(QuartzSchedulerFactory.class);

        List<JobDetail> jobDetails = new ArrayList<>();
        List<Trigger> cronTriggers = new ArrayList<>();

        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(QuartzScheduled.class);
		for (Object confBean : beanMap.values()) {

			Class<?> jobClass = (Class<? extends Job>) confBean.getClass();

			QuartzScheduled conf = jobClass.getAnnotation(QuartzScheduled.class);
			
			String jobName = conf.jobName();
			String jobGroup = conf.jobGroup();
			String jobParameter = getEnvironmentStringValue(jobName,"jobParameter", conf.jobParameter());
			String description = getEnvironmentStringValue(jobName, "description", conf.description());
			String cron = getEnvironmentStringValue(jobName, "cron", conf.cron());

			/** 作业程序 */
            JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
            jobDetailFactoryBean.setJobClass(jobClass);
            jobDetailFactoryBean.setName(jobName);
            jobDetailFactoryBean.setGroup(jobGroup);
            jobDetailFactoryBean.setDescription(description);
            if (org.apache.commons.lang3.StringUtils.isNotBlank(jobParameter)){
                jobDetailFactoryBean.setJobDataAsMap(JSON.parseObject(jobParameter, Map.class));
            }
            jobDetailFactoryBean.setDurability(true);
            //内部将jobDetails赋值
            jobDetailFactoryBean.afterPropertiesSet();
            //获取jobDetail
            JobDetail jobDetail = jobDetailFactoryBean.getObject();


			/** cron触发器 */
            CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
            cronTriggerFactoryBean.setJobDetail(jobDetail);
            cronTriggerFactoryBean.setName(jobName);
            cronTriggerFactoryBean.setGroup(jobGroup);
            cronTriggerFactoryBean.setDescription(description);
            cronTriggerFactoryBean.setCronExpression(cron);

            try {
                //内部将cronTrigger赋值
                cronTriggerFactoryBean.afterPropertiesSet();
            } catch (ParseException e) {
                log.error(e.getMessage(),e);
            }
            //获取cronTrigger
            CronTrigger cronTrigger = cronTriggerFactoryBean.getObject();


            jobDetails.add(jobDetail);
            cronTriggers.add(cronTrigger);
		}

		if (CollectionUtils.isEmpty(jobDetails) || CollectionUtils.isEmpty(cronTriggers)){return;}

        /** 构建SchedulerFactoryBean对象来初始化任务 */
        BeanDefinitionBuilder factory = BeanDefinitionBuilder.genericBeanDefinition(SchedulerFactoryBean.class);
        factory.setScope(BeanDefinition.SCOPE_SINGLETON);
        factory.addPropertyValue("jobDetails",jobDetails.toArray(new JobDetail[jobDetails.size()]));
        factory.addPropertyValue("triggers",cronTriggers.toArray(new CronTrigger[cronTriggers.size()]));
        /**  定时任务Job对象的实例化过程是在Quartz中进行的，而service Bean是由Spring容器管理的，
             Quartz根本就察觉不到而service Bean的存在，故而无法将而service Bean装配到Job对象中
             因此需要将job bean也纳入到spring容器中  为了可以使用@autowire 注入service类
             参考文档：https://blog.csdn.net/pengjunlee/article/details/78965877 */
        factory.addPropertyValue("jobFactory",quartzSchedulerFactory);

        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
        defaultListableBeanFactory.registerBeanDefinition("schedulerFactoryBean", factory.getBeanDefinition());
    }


	/**
	 * 获取配置中的任务属性值，environment没有就用注解中的值
	 * @param jobName		任务名称
	 * @param fieldName		属性名称
	 * @param defaultValue  默认值
	 * @return
	 */
	private String getEnvironmentStringValue(String jobName, String fieldName, String defaultValue) {
		String key = prefix + jobName + "." + fieldName;
		String value = environment.getProperty(key);
		if (StringUtils.hasText(value)) {
			return value;
		}
		return defaultValue;
	}
	
	private int getEnvironmentIntValue(String jobName, String fieldName, int defaultValue) {
		String key = prefix + jobName + "." + fieldName;
		String value = environment.getProperty(key);
		if (StringUtils.hasText(value)) {
			return Integer.valueOf(value);
		}
		return defaultValue;
	}
	
	private long getEnvironmentLongValue(String jobName, String fieldName, long defaultValue) {
		String key = prefix + jobName + "." + fieldName;
		String value = environment.getProperty(key);
		if (StringUtils.hasText(value)) {
			return Long.valueOf(value);
		}
		return defaultValue;
	}
	
	private boolean getEnvironmentBooleanValue(String jobName, String fieldName, boolean defaultValue) {
		String key = prefix + jobName + "." + fieldName;
		String value = environment.getProperty(key);
		if (StringUtils.hasText(value)) {
			return Boolean.valueOf(value);
		}
		return defaultValue;
	}
}
