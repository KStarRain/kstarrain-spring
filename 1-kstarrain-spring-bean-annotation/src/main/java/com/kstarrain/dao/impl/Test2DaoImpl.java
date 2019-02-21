package com.kstarrain.dao.impl;

import com.kstarrain.dao.ITestDao;

public class Test2DaoImpl implements ITestDao {

    private String name;
    private Integer age;

    @Override
    public  void test() {
        System.out.println("执行 dao_02 方法 -- " + name + "," + age);
    }

    public Test2DaoImpl() {
        System.out.println("构造Test2DaoImpl()");
    }

    public Test2DaoImpl(String name, Integer age) {
        System.out.println("构造Test2DaoImpl("+ name + "," + age + ")");
        this.name = name;
        this.age = age;
    }

}
