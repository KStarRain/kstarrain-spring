package com.kstarrain;

import com.alibaba.fastjson.JSON;
import com.kstarrain.annotation.test.TestAnnotation;
import com.kstarrain.service.IStudentService;
import com.kstarrain.service.impl.TestAnnotationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Method;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:config/application.xml"})
@Slf4j
public class TestAnnotationTest extends AbstractJUnit4SpringContextTests {


    @Test
    public void test(){
        System.out.println("========================================================");

        try {
            //1. 反射获取到类信息
            Class<?> forName = Class.forName("com.kstarrain.service.impl.TestAnnotationService");
            //2. 获取到当前类（不包含继承）所有的方法
            Method[] declaredMethods = forName.getDeclaredMethods();
            //3. 遍历每个方法的信息
            for (Method method : declaredMethods) {
                System.out.println("方法名称:" + method.getName());
                //4. 获取方法上面的注解
                TestAnnotation annotation = method.getDeclaredAnnotation(TestAnnotation.class);
                if(annotation == null) {
                    System.out.println("该方法上没有加注解....");
                }else {
                    System.out.println("Id:" + annotation.id());
                    System.out.println("Name:" + annotation.name());
                    System.out.println("Arrays:" + JSON.toJSONString(annotation.arrays()));
                    System.out.println("Description:" + annotation.description());
                }
                System.out.println("--------------------------");
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        System.out.println("========================================================");
    }
}
