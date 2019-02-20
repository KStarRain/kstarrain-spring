package com.kstarrain.service;

import com.github.pagehelper.Page;
import com.kstarrain.pojo.Student;

import java.text.ParseException;
import java.util.List;

public interface IStudentService {

    List<Student> findAllStudent();

    /**
     * @param pageNum  当前页
     * @param pageSize 每页显示条数
     * @param count    是否计算总数
     * @return
     */
    Page<Student> findAllStudentInPage(int pageNum, int pageSize, boolean count);

    void insertStudent(Student student);

    /** 编程式事务 */
    void programmingTransaction(Student student1, Student student2);

    /** 注解事务 */
    void annotatedTransaction(Student student1, Student student2);

}
