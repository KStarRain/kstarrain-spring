package com.kstarrain;

import com.kstarrain.controller.StudentController;
import com.kstarrain.dao.StudentDao;
import com.kstarrain.dao.impl.StudentDaoImpl;
import com.kstarrain.pojo.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:config/application.xml"})
public class JdbcRequestTest extends AbstractJUnit4SpringContextTests {

    private static String name = "貂蝉' or 1 ='1";


    @Test
    public void findStudentByName() {

        StudentDao studentDao = super.applicationContext.getBean("studentDaoImpl", StudentDao.class);
        /** sql 注入*/
        List<Student> students1 = studentDao.findStudentByName(name);
        System.out.println(students1);


        /** 避免sql 注入*/
        List<Student> students2 = studentDao.findStudentByName2(name);
        System.out.println(students2);

    }




    @Test
    public void findAllStudent() {

        StudentController studentController = super.applicationContext.getBean("studentController", StudentController.class);
        List<Student> allStudent = studentController.findAllStudent();
        System.out.println(allStudent);
    }


    @Test
    public void insertStudent() {

        StudentController studentController = super.applicationContext.getBean("studentController", StudentController.class);
        studentController.insertStudent();
    }
}
