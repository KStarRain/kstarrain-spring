package com.kstarrain.quartz.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用quartz@QuartzScheduledOnMethod注解
 */
@Component
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableQuartzScheduledOnMethod {
	String value() default "";
}
