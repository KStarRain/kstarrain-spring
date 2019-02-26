package com.kstarrain.quartz.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * quartz 调度注解
 * 只能放在方法上，且该方法不能有参数(因此无法传递初始化参数)
 * 并且要配合@EnableQuartzScheduledOnMethod使用
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface QuartzScheduledOnMethod {

	/**
	 * 作业名称(唯一)
	 * @return
	 */
	String jobName() default "";

	/**
	 * 作业分组
	 * @return
	 */
	String jobGroup() default "DEFAULT";


	/**
	 * 同一个作业内是否支持并发
	 * @return
	 */
	boolean concurrent() default false;

	/**
	 * cron表达式，用于控制作业触发时间
	 * @return
	 */
	String cron() default "";

}
