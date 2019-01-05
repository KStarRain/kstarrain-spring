package com.kstarrain.service;

import com.kstarrain.pojo.Student;

import java.util.List;

public interface IStudentService {

    List<Student> findAllStudent();

    void insertStudent();


}
