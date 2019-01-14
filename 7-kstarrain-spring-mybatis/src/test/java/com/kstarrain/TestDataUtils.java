package com.kstarrain;

import com.kstarrain.pojo.Student;
import com.kstarrain.service.IStudentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;



public class TestDataUtils {

    public static Student createStudent1() throws ParseException {
        Student student1 = new Student();
        student1.setId(UUID.randomUUID().toString().replace("-", ""));
        student1.setName("貂蝉Mm");
        student1.setBirthday(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1991-09-07 23:24:51"));
        student1.setMoney(new BigDecimal("1314.98"));
        student1.setCreateDate(new Date());
        student1.setUpdateDate(new Date());
        student1.setAliveFlag("1");
        return student1;
    }

    public static Student createStudent2() throws ParseException {
        Student student2 = new Student();
        student2.setId(UUID.randomUUID().toString().replace("-", ""));
        student2.setName("吕布Gg");
        student2.setBirthday(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1990-09-07 23:24:51"));
        student2.setMoney(new BigDecimal("521.98"));
        student2.setCreateDate(new Date());
        student2.setUpdateDate(new Date());
        student2.setAliveFlag("1");
        return student2;
    }


}
