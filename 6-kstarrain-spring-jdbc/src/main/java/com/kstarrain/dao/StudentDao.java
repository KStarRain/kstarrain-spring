package com.kstarrain.dao;

import com.kstarrain.pojo.Student;

import java.util.List;

/**
 * @author: DongYu
 * @create: 2019-01-05 16:41
 * @description:
 */
public interface StudentDao {

    /** sql 注入*/
    List<Student> findStudentByName(String name);

    /** 避免sql 注入*/
    List<Student> findStudentByName2(String name);


    List<Student> findAllStudent();

    int insertStudent(Student student);

    /** 错误的sql ID_HHHHHHHHHHHHHHHH */
    int insertStudentError(Student student);


}
