package com.kstarrain.service.impl;


import com.kstarrain.dao.ITestDao;
import com.kstarrain.service.ITestService;

public class TestServiceImpl implements ITestService {

    private ITestDao testDao;

    public void setTestDao(ITestDao testDao) {
        this.testDao = testDao;
    }

    public  void test() {
        System.out.println("执行service方法");
        testDao.test();
    }

}
