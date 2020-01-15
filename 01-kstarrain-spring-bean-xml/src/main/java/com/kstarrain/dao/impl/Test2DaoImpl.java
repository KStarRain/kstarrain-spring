package com.kstarrain.dao.impl;

import com.kstarrain.dao.ITestDao;
import org.springframework.stereotype.Repository;

@Repository
public class Test2DaoImpl implements ITestDao {

    @Override
    public  void test() {
        System.out.println("执行 dao_02 方法");
    }

}
