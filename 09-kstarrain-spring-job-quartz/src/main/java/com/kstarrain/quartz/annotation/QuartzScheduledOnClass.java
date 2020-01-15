package com.kstarrain.quartz.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: DongYu
 * @create: 2019-02-22 12:25
 * @description: quartz 调度注解
 * 				 只能放在类上，并且该类需要继承QuartzJobBean类 或者实现Job类
 * 				 如果同一个作业不需要并发执行 添加@DisallowConcurrentExecution注解
 */
@Component
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface QuartzScheduledOnClass {

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
