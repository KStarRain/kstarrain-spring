package com.kstarrain.dao.impl;

import com.kstarrain.dao.ITestDao;
import org.springframework.stereotype.Repository;

@Repository
public class TestDaoImpl implements ITestDao {


    @Override
    public String test() {
        System.out.println("执行 dao 方法");
        return "hello success";
    }

}
