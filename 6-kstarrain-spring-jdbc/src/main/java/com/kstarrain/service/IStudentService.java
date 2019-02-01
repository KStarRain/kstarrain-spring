package com.kstarrain.service;

import com.kstarrain.pojo.Student;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

public interface IStudentService {

    List<Student> findAllStudent();

    int insertStudent(Student student);

    /** 编程式事务 */
    void programmingTransaction();

    /** xml中配置以方法为单位配置aop事务 */
    void xmlTransaction();

    /** 注解事务 */
//    @Transactional(rollbackFor = Exception.class)
    void annotatedTransaction();

    /** 注解事务(错误演示)*/
    void annotatedTransaction_error();


    /** 事务传播方式测试01 -- 编程式 */
    void propagation_programming01();

    /** 事务传播方式测试02 -- 编程式 */
    void propagation_programming02();
}
