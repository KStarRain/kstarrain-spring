package com.kstarrain.service;

import com.kstarrain.pojo.Student;

import java.text.ParseException;
import java.util.List;

public interface IStudentService {

    List<Student> findAllStudent();

    void insertStudent();

    /** 编程式事务 */
    void programmingTransaction(Student student1, Student student2);

    /** 注解事务 */
    void annotatedTransaction(Student student1, Student student2);

    /** 隔离级别测试 一次事务中 2次查询同一条数据(测试不可重复读) */
    void isolationLevel_findById(String id);

    /** 隔离级别测试 一次事务中 修改同一条数据 */
    void isolationLevel_updateById(String id);
}
