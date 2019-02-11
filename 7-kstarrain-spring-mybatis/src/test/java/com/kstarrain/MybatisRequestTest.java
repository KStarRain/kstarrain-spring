package com.kstarrain;

import com.kstarrain.pojo.Student;
import com.kstarrain.service.IStudentService;
import com.kstarrain.utils.TestDataUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:config/application.xml"})
@Slf4j
public class MybatisRequestTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    IStudentService studentService;

    @Test
    public void findAllStudent() {
        System.out.println("===============================================================================");

        List<Student> students = studentService.findAllStudent();
        System.out.println(students.size());

        System.out.println("===============================================================================");
    }



    @Test
    public void insertStudent() {
        System.out.println("==========================================================================");

        studentService.insertStudent(TestDataUtils.createStudent1());

        System.out.println("==========================================================================");
    }



    /** 编程式事务 */
    @Test
    public void programmingTransaction() throws ParseException {
        System.out.println("===============================================================================");

        try {
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
            System.out.println(studentService.getClass().getName());
            studentService.annotatedTransaction(TestDataUtils.createStudent1(),TestDataUtils.createStudent2());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        System.out.println("===============================================================================");
    }


}
