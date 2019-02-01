package com.kstarrain;

import com.kstarrain.pojo.Student;
import com.kstarrain.service.IStudentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class MybatisRequestTest extends AbstractJUnit4SpringContextTests {


    @Test
    public void findAllStudent() {
        System.out.println("===============================================================================");

        IStudentService studentService = super.applicationContext.getBean("studentServiceImpl", IStudentService.class);
        List<Student> students = studentService.findAllStudent();
        System.out.println(students.size());

        System.out.println("===============================================================================");
    }



    @Test
    public void insertStudent() {
        System.out.println("==========================================================================");

        IStudentService studentService = super.applicationContext.getBean("studentServiceImpl", IStudentService.class);
        studentService.insertStudent();

        System.out.println("==========================================================================");
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




    /** 声明式事务 */
    @Test
    public void declarativeTransaction() {
        System.out.println("===============================================================================");

        try {
            IStudentService studentService = super.applicationContext.getBean("studentServiceImpl", IStudentService.class);
//            studentService.declarativeTransaction(TestDataUtils.createStudent1(),TestDataUtils.createStudent2());
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


    /** 隔离级别测试 一次事务中 2次查询同一条数据(测试不可重复读) */
    @Test
    public void isolationLevel_findById() throws ParseException {
        System.out.println("===============================================================================");


        try {
            IStudentService studentService = super.applicationContext.getBean("studentServiceImpl", IStudentService.class);
            studentService.isolationLevel_findById("0eeea1902d244721a1a6e88135e91e01");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        System.out.println("===============================================================================");
    }


    /** 脏读测试 一次事务中 修改同一条数据 */
    @Test
    public void isolationLevel_updateById() throws ParseException {
        System.out.println("===============================================================================");

        try {
            IStudentService studentService = super.applicationContext.getBean("studentServiceImpl", IStudentService.class);
            studentService.isolationLevel_updateById("0eeea1902d244721a1a6e88135e91e01");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        System.out.println("===============================================================================");
    }

}