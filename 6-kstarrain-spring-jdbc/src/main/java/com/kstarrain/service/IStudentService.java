package com.kstarrain.service;

import com.kstarrain.pojo.Student;

import java.text.ParseException;
import java.util.List;

public interface IStudentService {

    List<Student> findAllStudent();

    int insertStudent(Student student);

    /** 编程式事务 */
    void programmingTransaction(Student student1,Student student2);

    /** 注解事务 */
    void annotatedTransaction(Student student1,Student student2);



}
