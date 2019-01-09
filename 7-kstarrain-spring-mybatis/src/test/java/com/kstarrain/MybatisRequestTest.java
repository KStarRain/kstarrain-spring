package com.kstarrain;

import com.kstarrain.controller.StudentController;
import com.kstarrain.pojo.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:config/application.xml"})
public class MybatisRequestTest extends AbstractJUnit4SpringContextTests {

    @Test
    public void findAllStudent() {

        StudentController studentController = super.applicationContext.getBean("studentController", StudentController.class);
        List<Student> students = studentController.findAllStudent();
        System.out.println(students);
    }


    @Test
    public void insertStudent() {

        StudentController studentController = super.applicationContext.getBean("studentController", StudentController.class);
        studentController.insertStudent();
    }
}
