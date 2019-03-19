package com.kstarrain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.kstarrain.pojo.Student;
import com.kstarrain.utils.TestDataUtils;
import com.kstarrain.utils.bean.BeanConvertUtils;
import com.kstarrain.vo.StudentVO;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: DongYu
 * @create: 2019-03-18 09:23
 * @description:
 */
public class BeanConvertTest {

    @Test
    public void test1(){

        Student student = TestDataUtils.createStudent1();

        Map<String, Object> studentMap = BeanConvertUtils.beanToMap(student);
        System.out.println(studentMap);
    }

    @Test
    public void test2(){

        List<Student> students = new ArrayList<>();
        students.add(TestDataUtils.createStudent1());
        students.add(TestDataUtils.createStudent2());

        List<Map<String, Object>> studentsMap = BeanConvertUtils.beanToMapInList(students);
        System.out.println(studentsMap);
    }

    @Test
    public void test3(){

        Student student = TestDataUtils.createStudent1();

        StudentVO studentVO = BeanConvertUtils.beanToBean(student, StudentVO.class);
        System.out.println(studentVO);
    }

    @Test
    public void test4(){

        List<Student> students = new ArrayList<>();
        students.add(TestDataUtils.createStudent1());
        students.add(TestDataUtils.createStudent2());

        List<StudentVO> studentVOS = BeanConvertUtils.beanToBeanInList(students, StudentVO.class);
        System.out.println(JSON.toJSONString(studentVOS));
    }

    @Test
    public void test5(){

        List<Student> students = new ArrayList<>();
        students.add(TestDataUtils.createStudent1());
        students.add(TestDataUtils.createStudent2());

        String s = JSON.toJSONString(students);


        JSONArray arr = JSONArray.parseArray(s);
        List<Student> list = arr.toJavaList(Student.class);

    }
}
