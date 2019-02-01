package com.kstarrain.service;


import com.kstarrain.annotation.MyTransactional;
import com.kstarrain.pojo.Student;

public interface IStudentService {

    void insertStudent(Student student);

    /** 注解事务 */
//    @MyTransactional
    void annotatedTransaction();


    /** 注解事务 错误演示 */
    void annotatedTransaction_error();



}
