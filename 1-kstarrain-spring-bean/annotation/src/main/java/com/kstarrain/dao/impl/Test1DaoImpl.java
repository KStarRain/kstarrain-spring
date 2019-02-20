package com.kstarrain.dao.impl;

import com.kstarrain.dao.ITestDao;
import org.springframework.stereotype.Repository;

@Repository("test1DaoImpl")
public class Test1DaoImpl implements ITestDao {


    @Override
    public  void test() {
        System.out.println("执行 dao_01 方法");
    }

}
