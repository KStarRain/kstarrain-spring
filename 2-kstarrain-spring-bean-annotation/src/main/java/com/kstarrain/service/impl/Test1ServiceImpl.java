package com.kstarrain.service.impl;

import com.alibaba.fastjson.JSON;
import com.kstarrain.dao.ITestDao;
import com.kstarrain.service.ITestService;
import com.kstarrain.vo.Human;
import com.kstarrain.vo.Level;
import com.kstarrain.vo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class Test1ServiceImpl implements ITestService {

    @Autowired
    @Qualifier("test1DaoImpl")
    private ITestDao iTestDao;


    public  void test() {
        System.out.println("执行 service_01 方法");


        Human human = new Human();
        human.setSex("男");

        Student student = new Student();
        student.setName("董宇");
        student.setBirthday(new Date());
        student.setLevel(Level.TWO);
        human.setStudent(student);

        String s = JSON.toJSONString(human);
        System.out.println(s);

        Human human1 = JSON.parseObject(s, Human.class);

        iTestDao.test();
    }

}
