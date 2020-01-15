package com.kstarrain;

import com.kstarrain.pojo.Student;
import com.kstarrain.service.IStudentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 * @author: DongYu
 * @create: 2019-01-27 16:46
 * @description: 事务模板测试 动态代理http://www.cnblogs.com/zby9527/p/6945756.html  https://blog.csdn.net/yhl_jxy/article/details/80635012
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:config/application.xml"})
@Slf4j
public class TransactionTemplateTest {

    private static String ID = "d7fbbfd87a08408ba994dea6be435ada";

    @Autowired
    IStudentService studentService;

    @Autowired
    PlatformTransactionManager transactionManager;

    //spring配置文件中已注入事务模板类 类似 TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
    @Autowired
    TransactionTemplate transactionTemplate;


    /** 自定义隔离级别，传播方式的事务 */
    @Test
    public void customTransaction(){
        System.out.println("===============================================================================");

        try {

            //定义事务
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            //设置事务隔离等级
            def.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
            //设置事务传播方式
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

            TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager, def);

            List<Student> students = transactionTemplate.execute(new TransactionCallback<List<Student>>() {
                @Override
                public List<Student> doInTransaction(TransactionStatus transactionStatus) {

                    List<Student>  students = studentService.findAllStudent();
                    return students;
                }
            });

            System.out.println("查询结果:"+ students.size()+ "条");
            System.out.println("===============================================================================");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }



    /** 默认的隔离级别，传播方式事务 */
    @Test
    public void defaultTransaction(){
        System.out.println("===============================================================================");

        try {

            List<Student> students = transactionTemplate.execute(new TransactionCallback<List<Student>>() {
                @Override
                public List<Student> doInTransaction(TransactionStatus transactionStatus) {

                    List<Student>  students = studentService.findAllStudent();
                    return students;
                }
            });

            System.out.println("查询结果:"+ students.size()+ "条");
            System.out.println("===============================================================================");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }
}
