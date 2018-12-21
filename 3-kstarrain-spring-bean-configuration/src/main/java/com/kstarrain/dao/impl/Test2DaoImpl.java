package com.kstarrain.dao.impl;

import com.kstarrain.dao.ITestDao;

public class Test2DaoImpl implements ITestDao {


    private String name;
    private String name2;

    @Override
    public  void test() {
        System.out.println("执行 dao_02 方法" + name + name2);
    }

    public Test2DaoImpl() {
        System.out.println("构造Test2DaoImpl()");
    }

    public Test2DaoImpl(String name, String name2) {
        System.out.println("构造Test2DaoImpl("+ name + "," + name2 + ")");
        this.name = name;
        this.name2 = name2;
    }
}
