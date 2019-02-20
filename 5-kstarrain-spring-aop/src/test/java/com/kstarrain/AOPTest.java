package com.kstarrain;


import com.kstarrain.dao.StudentDao;
import com.kstarrain.service.IStudentService;
import com.kstarrain.utils.TestDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:config/application.xml"})
@Slf4j
public class AOPTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    IStudentService studentService;


    /** 注解事务 文档 https://www.cnblogs.com/wlwl/p/10092494.html
     *              https://blog.csdn.net/lz710117239/article/details/78787280
     * */
    @Test
    public void annotatedTransaction(){
        System.out.println("===============================================================================");
        try {
            System.out.println(studentService.getClass().getName());
            System.out.println("--------------------------");
            studentService.annotatedTransaction();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        System.out.println("===============================================================================");
    }



    /** 注解事务(错误演示)*/
    @Test
    public void annotatedTransaction_error(){
        System.out.println("===============================================================================");
        try {
            System.out.println(studentService.getClass().getName());
            System.out.println("--------------------------");
            studentService.annotatedTransaction_error();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        System.out.println("===============================================================================");
    }


    @Test
    public void insertStudent(){
        System.out.println("===============================================================================");
        try {
            System.out.println(studentService.getClass().getName());
            System.out.println("--------------------------");
            studentService.insertStudent(TestDataUtil.createStudent1());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        System.out.println("===============================================================================");
    }





}
