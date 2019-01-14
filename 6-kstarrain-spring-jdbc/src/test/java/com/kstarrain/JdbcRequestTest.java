package com.kstarrain;

import com.kstarrain.dao.StudentDao;
import com.kstarrain.pojo.Student;

import com.kstarrain.service.IStudentService;
import com.kstarrain.utils.TestDataUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.BeansException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


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

        IStudentService studentService = super.applicationContext.getBean("studentServiceImpl", IStudentService.class);
        List<Student> allStudent = studentService.findAllStudent();
        System.out.println(allStudent.size());

        System.out.println("===============================================================================");
    }


    @Test
    public void insertStudent() {
        System.out.println("===============================================================================");

        try {
            IStudentService studentService = super.applicationContext.getBean("studentServiceImpl", IStudentService.class);

            int num = studentService.insertStudent(TestDataUtils.createStudent1());
            System.out.println(num);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        System.out.println("===============================================================================");
    }


    /** 编程式事务 */
    @Test
    public void programmingTransaction() throws ParseException {
        System.out.println("===============================================================================");

        try {
            IStudentService studentService = super.applicationContext.getBean("studentServiceImpl", IStudentService.class);
            studentService.programmingTransaction(TestDataUtils.createStudent1(),TestDataUtils.createStudent2());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        System.out.println("===============================================================================");
    }


    /** 注解事务 */
    @Test
    public void annotatedTransaction() throws ParseException {
        System.out.println("===============================================================================");

        try {
            IStudentService studentService = super.applicationContext.getBean("studentServiceImpl", IStudentService.class);
            studentService.annotatedTransaction(TestDataUtils.createStudent1(),TestDataUtils.createStudent2());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        System.out.println("===============================================================================");
    }

}
