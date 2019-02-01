package com.kstarrain.service.impl;

import com.kstarrain.dao.StudentDao;
import com.kstarrain.pojo.Student;
import com.kstarrain.service.IStudentService;
import com.kstarrain.utils.TestDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
@Slf4j
public class StudentServiceImpl implements IStudentService {

    @Autowired
    StudentDao studentDao;

    //spring配置文件中已注入org.springframework.jdbc.datasource.DataSourceTransactionManager事务管理类
    @Autowired
    PlatformTransactionManager transactionManager;


    public  List<Student> findAllStudent() {
        List<Student> students = studentDao.findAllStudent();
        return students;
    }

    @Override
    public int insertStudent(Student student){
        int num = studentDao.insertStudent(student);
        return num;
    }


    /** 编程式事务 */
    @Override
    public void programmingTransaction(){

        List<Student> students = studentDao.findAllStudent();

        //定义事务
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //设置事务隔离等级
        def.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        //设置事务传播方式
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        //获取事务对象
        TransactionStatus status = transactionManager.getTransaction(def);;

        try {

            //插入数据
            studentDao.insertStudent(TestDataUtil.createStudent1());

            int a = 5/0;

            //插入数据
            studentDao.insertStudent(TestDataUtil.createStudent2());

            //事务提交
            transactionManager.commit(status);

        } catch (Exception e) {
            //如果事务提交失败 ，回滚事务
            transactionManager.rollback(status);
            throw e;
        }
    }


    /** xml中配置以方法为单位配置aop事务 */
    @Override
    public void xmlTransaction(){

        //插入数据
        studentDao.insertStudent(TestDataUtil.createStudent1());

        int a = 5/0;

        //插入数据
        studentDao.insertStudent(TestDataUtil.createStudent2());
    }


    /** 注解事务 */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void annotatedTransaction(){

        //插入数据
        studentDao.insertStudent(TestDataUtil.createStudent1());

        int a = 5/0;

        //插入数据
        studentDao.insertStudent(TestDataUtil.createStudent2());
    }


    /** 注解事务(错误演示)*/
    @Override
    public void annotatedTransaction_error() {

        List<Student> students = studentDao.findAllStudent();

        this.annotatedTransaction();

    }


    /** 事务传播方式测试01 -- 编程式 */
    @Override
    public void propagation_programming01() {

        //定义事务
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        /** 设置事务传播方式
         *  PROPAGATION_REQUIRED：如果当前没有事务，就创建一个新事务，如果当前存在事务，就加入该事务，该设置是最常用的设置
         *  PROPAGATION_SUPPORTS：支持当前事务，如果当前存在事务，就加入该事务，如果当前不存在事务，就以非事务执行。
         *  PROPAGATION_MANDATORY：支持当前事务，如果当前存在事务，就加入该事务，如果当前不存在事务，就抛出异常。
         *  PROPAGATION_REQUIRES_NEW：创建新事务，无论当前存不存在事务，都创建新事务。
         *  PROPAGATION_NOT_SUPPORTED：以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。
         *  PROPAGATION_NEVER：以非事务方式执行，如果当前存在事务，则抛出异常。
         *  PROPAGATION_NESTED：如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行与PROPAGATION_REQUIRED类似的操作。
         *
         *
         * PROPAGATION_REQUIRES_NEW 和 PROPAGATION_NESTED 的最大区别在于, PROPAGATION_REQUIRES_NEW 完全是一个新的事务,
         * 而 PROPAGATION_NESTED 则是外部事务的子事务, 如果外部事务 commit, 潜套事务也会被 commit
         *
         *  */
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        //设置事务隔离等级
        def.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);

        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager, def);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                studentDao.insertStudent(TestDataUtil.createStudent1());
//                int a = 5/0;
                propagation_programming02();
//                int a = 5/0;
                System.out.println("123");
            }
        });
    }

    /** 事务传播方式测试02 -- 编程式 */
    @Override
    public void propagation_programming02() {
        //定义事务
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //设置事务传播方式
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        //设置事务隔离等级
        def.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);

        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager, def);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                studentDao.insertStudent(TestDataUtil.createStudent2());
//                int a = 5/0;
            }
        });
        System.out.println("===============断点==============");
    }
}
