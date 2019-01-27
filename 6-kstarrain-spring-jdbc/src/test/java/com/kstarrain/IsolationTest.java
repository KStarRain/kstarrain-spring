package com.kstarrain;


import com.kstarrain.dao.StudentDao;
import com.kstarrain.dao.impl.StudentDaoImpl;
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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: Dong Yu
 * @create: 2018-11-13 15:52
 * @description: spring事务隔离级别测试
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:config/application.xml"})
@Slf4j
public class IsolationTest extends AbstractJUnit4SpringContextTests {

    //  https://www.cnblogs.com/zwt1990/p/7096492.html

    private static String ID = "d7fbbfd87a08408ba994dea6be435ada";

    @Autowired
    StudentDao studentDao;

    //spring配置文件中已注入org.springframework.jdbc.datasource.DataSourceTransactionManager事务管理类
    @Autowired
    PlatformTransactionManager transactionManager;


    /** 隔离级别测试 修改数据(测试脏读) */
    @Test
    public void isolation_find(){
        System.out.println("===============================================================================");

        //定义事务
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //设置事务隔离等级
        def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        //设置事务传播方式
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        //获取事务对象
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            List<Student>  students = studentDao.findAllStudent();
            System.out.println("查询结果:"+ students.size()+ "条");
            if (students.size() > 0){
                System.out.println("money = " +students.get(0).getMoney());
            }
            transactionManager.commit(status);
        } catch (Exception e) {
            //如果事务提交失败 ，回滚事务
            transactionManager.rollback(status);
            log.error(e.getMessage(),e);
        }
        System.out.println("===============================================================================");
    }


    /** 隔离级别测试 新增数据(测试脏读)*/
    @Test
    public void isolation_insert(){
        System.out.println("===============================================================================");
        //定义事务
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //设置事务隔离等级
        def.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        //设置事务传播方式
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        //获取事务对象
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            Student student = new Student();
            student.setId(ID);
            student.setName("貂蝉Mm");
            student.setBirthday(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1991-09-07 23:24:51"));
            student.setMoney(new BigDecimal("0.00"));
            student.setCreateDate(new java.util.Date());
            student.setUpdateDate(new Date());
            student.setAliveFlag("1");
            int num = studentDao.insertStudent(student);
            int a = 5/0;
            transactionManager.commit(status);
        } catch (Exception e) {
            //如果事务提交失败 ，回滚事务
            transactionManager.rollback(status);
            log.error(e.getMessage(),e);
        }
        System.out.println("===============================================================================");
    }

    /** 隔离级别测试 修改数据(测试脏读) */
    @Test
    public void isolation_update(){
        System.out.println("===============================================================================");
        //定义事务
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //设置事务隔离等级
        def.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        //设置事务传播方式
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        //获取事务对象
        TransactionStatus status = transactionManager.getTransaction(def);;
        try {
            //update t_student set MONEY = MONEY + 1,UPDATE_DATE = SYSDATE() where ALIVE_FLAG = '1' and Id = ?
            int num = studentDao.updateMoneyById(ID);
//            int a = 5/0;
            transactionManager.commit(status);
        } catch (Exception e) {
            //如果事务提交失败 ，回滚事务
            transactionManager.rollback(status);
            log.error(e.getMessage(),e);
        }
        System.out.println("===============================================================================");
    }


    /** 隔离级别测试 查询同一条数据 (测试不可重复读 针对update)*/
    @Test
    public void isolation_repeatable_read() {
        System.out.println("===============================================================================");

        //定义事务
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //设置事务隔离等级
        def.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        //设置事务传播方式
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        //获取事务对象
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            Student student1 = studentDao.findStudentById(ID);
            System.out.println("money = " + student1.getMoney());

            System.out.println("=============");

            Student student2 = studentDao.findStudentById(ID);
            System.out.println("money = " + student2.getMoney());
            transactionManager.commit(status);
        } catch (Exception e) {
            //如果事务提交失败 ，回滚事务
            transactionManager.rollback(status);
            log.error(e.getMessage(),e);
        }
        System.out.println("===============================================================================");
    }


    /** 隔离级别测试 查询数据行数(测试幻读 针对insert/delete)*/
    @Test
    public void isolationLevel_phantom_read(){
        System.out.println("===============================================================================");

        //定义事务
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //设置事务隔离等级
        def.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        //设置事务传播方式
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        //获取事务对象
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            List<Student>  students1 = studentDao.findAllStudent();
            System.out.println("查询结果:"+ students1.size()+ "条");

            System.out.println("=============");

            List<Student>  students2 = studentDao.findAllStudent();
            System.out.println("查询结果:"+ students2.size()+ "条");

            transactionManager.commit(status);
        } catch (Exception e) {
            //如果事务提交失败 ，回滚事务
            transactionManager.rollback(status);
            log.error(e.getMessage(),e);
        }
        System.out.println("===============================================================================");
    }

    /** 隔离级别测试 测试幻读 01 */
    @Test
    public void isolationLevel_phantom_read_01(){

        //定义事务
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //设置事务隔离等级
        def.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        //设置事务传播方式
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        //获取事务对象
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            List<Student>  students1 = studentDao.findStudentByName2("貂蝉Mm");
            System.out.println("查询结果:"+ students1.size()+ "条");

            System.out.println("=============");

            List<Student>  students2 = studentDao.findStudentByName2("貂蝉Mm");
            System.out.println("查询结果:"+ students2.size()+ "条");

            Student student = new Student();
            student.setId(ID);
            student.setName("貂蝉Mm");
            student.setBirthday(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1991-09-07 23:24:51"));
            student.setMoney(new BigDecimal("0.00"));
            student.setCreateDate(new java.util.Date());
            student.setUpdateDate(new Date());
            student.setAliveFlag("1");

            int num = studentDao.insertStudent(student);
            System.out.println("一共影响行数：" + num);

            transactionManager.commit(status);
        } catch (Exception e) {
            //如果事务提交失败 ，回滚事务
            transactionManager.rollback(status);
            log.error(e.getMessage(),e);
        }
        System.out.println("===============================================================================");

    }




}
