package com.kstarrain;

import com.kstarrain.controller.StudentController;
import com.kstarrain.dao.StudentDao;
import com.kstarrain.pojo.Student;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:config/application.xml"})
@Slf4j
public class JdbcRequestTest extends AbstractJUnit4SpringContextTests {

    private static String name = "貂蝉' or 1 ='1";


    @Test
    public void findStudentByName() {
        System.out.println("===============================================================================");

        StudentDao studentDao = super.applicationContext.getBean("studentDaoImpl", StudentDao.class);

        /** sql 注入*/
        List<Student> students1 = studentDao.findStudentByName(name);
        System.out.println(students1.size());
        System.out.println("===============================================================================");


        /** 避免sql 注入*/
        List<Student> students2 = studentDao.findStudentByName2(name);
        System.out.println(students2.size());
        System.out.println("===============================================================================");

    }




    @Test
    public void findAllStudent() {
        System.out.println("===============================================================================");

        log.info("info test");
        log.debug("debug test");

        System.out.println("===============================================================================");

        StudentController studentController = super.applicationContext.getBean("studentController", StudentController.class);


        List<Student> allStudent = studentController.findAllStudent();
        System.out.println(allStudent.size());
        System.out.println("===============================================================================");
    }


    @Test
    public void insertStudent() {

        StudentController studentController = super.applicationContext.getBean("studentController", StudentController.class);
        studentController.insertStudent();
    }
}
