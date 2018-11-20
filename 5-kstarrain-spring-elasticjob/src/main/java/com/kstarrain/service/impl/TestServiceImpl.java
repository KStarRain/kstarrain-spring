package com.kstarrain.service.impl;

import com.kstarrain.dao.ITestDao;
import com.kstarrain.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements ITestService {

    @Autowired
    private ITestDao iTestDao;


    public String test() {
        System.out.println("执行 service 方法");
        return iTestDao.test();
    }

}
