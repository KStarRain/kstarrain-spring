package com.kstarrain.quartz.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * quartz 注解配置类
 * 
 * <p>任务的配置只需要在Job类上加上此注解即可<p>
 *
 */
@Component
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface QuartzScheduled {

	/**
	 * 作业名称(唯一)
	 * @return
	 */
	String jobName();

	/**
	 * 作业分组
	 * @return
	 */
	String jobGroup() default "DEFAULT";

	/**
	 * cron表达式，用于控制作业触发时间
	 * @return
	 */
	String cron() default "";
	

	/**
	 * 作业自定义参数
	 * <p>作业自定义参数，可通过传递该参数为作业调度的业务方法传参，用于实现带参数的作业<p>
	 * <p>例：每次获取的数据量、作业实例从数据库读取的主键等<p>
	 * @return
	 */
	String jobParameter() default "";
	

	/**
	 * 作业描述信息
	 * @return
	 */
	String description() default "";

}
