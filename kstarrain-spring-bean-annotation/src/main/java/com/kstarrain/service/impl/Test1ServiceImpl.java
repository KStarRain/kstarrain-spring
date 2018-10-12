package com.kstarrain.service.impl;

import com.kstarrain.dao.ITestDao;
import com.kstarrain.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class Test1ServiceImpl implements ITestService {

    @Autowired
    @Qualifier("test1DaoImpl")
    private ITestDao iTestDao;


    public  void test() {
        System.out.println("执行 service_01 方法");
        iTestDao.test();
    }

}
