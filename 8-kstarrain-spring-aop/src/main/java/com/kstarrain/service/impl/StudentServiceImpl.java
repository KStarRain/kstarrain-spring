package com.kstarrain.service.impl;

import com.kstarrain.annotation.MyTransactional;
import com.kstarrain.dao.StudentDao;
import com.kstarrain.service.IStudentService;
import com.kstarrain.utils.TestDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class StudentServiceImpl implements IStudentService {

    @Autowired
    StudentDao studentDao;


    /** 注解事务 */
    @Override
    @MyTransactional
    public void annotatedTransaction(){

        //插入数据
        studentDao.insertStudent(TestDataUtil.createStudent1());

        int a = 5/0;

        //插入数据
        studentDao.insertStudent(TestDataUtil.createStudent2());
    }




}
