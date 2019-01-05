package com.kstarrain.controller;

import com.alibaba.fastjson.JSON;
import com.kstarrain.pojo.Student;
import com.kstarrain.service.IStudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class StudentController {

    private final Logger log = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    IStudentService studentService;

    public List<Student> findAllStudent() {
        System.out.println("---------------------------------------");
        System.out.println("执行 controller 方法");
        List<Student> allStudent = studentService.findAllStudent();
        log.info(JSON.toJSONString(allStudent));
        return allStudent;
    }

}
