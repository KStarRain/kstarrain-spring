package com.kstarrain.service.impl;

import com.kstarrain.dao.StudentDao;
import com.kstarrain.pojo.Student;
import com.kstarrain.service.IStudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    public void programmingTransaction(Student student1,Student student2) {

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
            studentDao.insertStudent(student1);

            int a = 5/0;

            //插入数据
            studentDao.insertStudent(student2);

            //事务提交
            transactionManager.commit(status);

        } catch (Exception e) {
            //如果事务提交失败 ，回滚事务
            transactionManager.rollback(status);
            throw e;
        }
    }


    /** 注解事务 */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void annotatedTransaction(Student student1,Student student2){

        //插入数据
        studentDao.insertStudent(student1);

        int a = 5/0;

        //插入数据
        studentDao.insertStudent(student2);
    }


}
