package com.kstarrain.service.impl;

import com.kstarrain.mapper.StudentMapper;
import com.kstarrain.pojo.Student;
import com.kstarrain.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    StudentMapper studentMapper;


    public  List<Student> findAllStudent() {
        System.out.println("执行 service方法");
        List<Student> allStudent = studentMapper.findAllStudent();
        return allStudent;
    }

}
