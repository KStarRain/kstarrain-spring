package com.kstarrain.service.impl;

import com.kstarrain.dao.StudentDao;
import com.kstarrain.pojo.Student;
import com.kstarrain.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    StudentDao studentDao;


    public  List<Student> findAllStudent() {
        List<Student> students = studentDao.findAllStudent();
        return students;
    }

    @Override
    public void insertStudent() {
        try {
            System.out.println("执行 service方法");
            Student student1 = new Student();
            student1.setId(UUID.randomUUID().toString().replace("-", ""));
            student1.setName("貂蝉Mm");
            student1.setBirthday(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1991-09-07 23:24:51"));
            student1.setMoney(new BigDecimal("1314.98"));
            student1.setCreateDate(new Date());
            student1.setUpdateDate(new Date());
            student1.setAliveFlag("1");
            studentDao.insertStudent(student1);


            int a = 5/0;

            Student student2 = new Student();
            student2.setId(UUID.randomUUID().toString().replace("-", ""));
            student2.setName("吕布Gg");
            student2.setBirthday(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1990-09-07 23:24:51"));
            student2.setMoney(new BigDecimal("521.98"));
            student2.setCreateDate(new Date());
            student2.setUpdateDate(new Date());
            student2.setAliveFlag("1");
            studentDao.insertStudent(student2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
