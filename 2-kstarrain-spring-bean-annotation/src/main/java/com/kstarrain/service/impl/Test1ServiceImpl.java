package com.kstarrain.service.impl;

import com.alibaba.fastjson.JSON;
import com.kstarrain.dao.ITestDao;
import com.kstarrain.service.ITestService;
import com.kstarrain.vo.LogWithIp;
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


        String a = "{"
                + "\"content\": {"
                    + "\"content\": \"用户登录:WEB_admin_null\","
                    + "\"level\": \"INFO\","
                    + "\"module\": \"basic\","
                    + "\"operateDate\": 1542872209143,"
                    + "\"operating\": \"391d312db1e945198a4a1d04ab8c8bc7\","
                    + "\"operator\": \"4f3989d088114c0e865c460471f0694a,391d312db1e945198a4a1d04ab8c8bc7\","
                    + "\"type\": \"LBAME023\""
                    + "},"
                    + "\"ip\": \"180.168.49.246\","
                    + "\"platform\": \"web\","
                    + "\"userAgent\": \"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36\""
                + "}";
        System.out.println(a);

        LogWithIp logWithIp = JSON.parseObject(a, LogWithIp.class);
        System.out.println();
        System.out.println();
        System.out.println();


        iTestDao.test();
    }

}
