package com.kstarrain.controller;

import com.kstarrain.pojo.Student;
import com.kstarrain.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class StudentController {

    @Autowired
    IStudentService studentService;

    public List<Student> findAllStudent() {
        System.out.println("---------------------------------------");
        System.out.println("执行 controller 方法");
        List<Student> allStudent = studentService.findAllStudent();
        return allStudent;
    }

}
