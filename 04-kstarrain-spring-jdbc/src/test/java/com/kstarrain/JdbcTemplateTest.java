package com.kstarrain;

import com.kstarrain.dao.StudentDao;
import com.kstarrain.pojo.Student;
import com.kstarrain.service.DataImportService;
import com.kstarrain.service.IStudentService;
import com.kstarrain.utils.TestDataUtils;
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
public class JdbcTemplateTest extends AbstractJUnit4SpringContextTests {

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
    public void programmingTransaction(){
        System.out.println("===============================================================================");

        try {
            IStudentService studentService = super.applicationContext.getBean("studentServiceImpl", IStudentService.class);
            studentService.programmingTransaction();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        System.out.println("===============================================================================");
    }



    /** xml以方法为单位配置aop事务 */
    @Test
    public void xmlTransaction(){
        System.out.println("===============================================================================");

        try {
            IStudentService studentService = super.applicationContext.getBean("studentServiceImpl", IStudentService.class);
            studentService.xmlTransaction();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        System.out.println("===============================================================================");
    }



    /** 注解事务 文档 https://www.jianshu.com/p/5687e2a38fbc */
    @Test
    public void annotatedTransaction(){
        System.out.println("===============================================================================");

        try {
            IStudentService studentService = super.applicationContext.getBean("studentServiceImpl", IStudentService.class);
            System.out.println(studentService.getClass().getName());
            studentService.annotatedTransaction();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        System.out.println("===============================================================================");
    }

    /** 注解事务(错误演示) 注解一定要放在最外面的service上，否则无效*/
    @Test
    public void annotatedTransaction_error(){
        System.out.println("===============================================================================");

        try {
            IStudentService studentService = super.applicationContext.getBean("studentServiceImpl", IStudentService.class);
            studentService.annotatedTransaction_error();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        System.out.println("===============================================================================");
    }



    @Test
    public void dataImport(){
        System.out.println("===============================================================================");

        try {
            DataImportService dataImportService = super.applicationContext.getBean(DataImportService.class);
            dataImportService.dataImport();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        System.out.println("===============================================================================");
    }

}
