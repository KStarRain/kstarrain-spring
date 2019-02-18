package com.kstarrain.service.impl;

import com.kstarrain.dao.ITestDao;
import com.kstarrain.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements ITestService {

    @Autowired
    @Qualifier("test1DaoImpl")
//    @Qualifier("1111")
    private ITestDao iTestDao;


    public  void test() {
        System.out.println(iTestDao);
        System.out.println("执行 service 方法");
        iTestDao.test();
    }

}
