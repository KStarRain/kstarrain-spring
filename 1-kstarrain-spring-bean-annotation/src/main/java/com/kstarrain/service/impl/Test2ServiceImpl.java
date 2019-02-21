package com.kstarrain.service.impl;

import com.kstarrain.dao.ITestDao;
import com.kstarrain.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class Test2ServiceImpl implements ITestService {

    @Autowired
    @Qualifier("test2DaoImpl")
    private ITestDao iTestDao;


    public  void test() {
        System.out.println("执行 service_02 方法");
        iTestDao.test();
    }

}
