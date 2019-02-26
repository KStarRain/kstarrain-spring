package com.kstarrain.quartz.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: DongYu
 * @create: 2019-02-22 12:25
 * @description: 启用@QuartzScheduledOnMethod注解
 */
@Component
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableQuartzScheduledOnMethod {
	String value() default "";
}
