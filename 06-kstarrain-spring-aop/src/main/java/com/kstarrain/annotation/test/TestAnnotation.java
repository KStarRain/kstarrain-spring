package com.kstarrain.annotation.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestAnnotation {

    //自定义注解的属性
    int id() default 0;

    String name() default "默认名字";

    String[] arrays();

    String description() default "默认描述";

}
