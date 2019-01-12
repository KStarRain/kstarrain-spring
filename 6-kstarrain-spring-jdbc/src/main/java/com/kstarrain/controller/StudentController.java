package com.kstarrain.controller;

import com.kstarrain.pojo.Student;
import com.kstarrain.service.IStudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Slf4j
public class StudentController {


    @Autowired
    IStudentService studentService;


    public List<Student> findAllStudent() {
        log.info("info findAllStudent");
        log.debug("debug findAllStudent");
        List<Student> allStudent = studentService.findAllStudent();
        return allStudent;
    }


    public void insertStudent() {
        studentService.insertStudent();
    }

}
