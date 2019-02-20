package com.kstarrain.request;


import com.kstarrain.controller.GoodsController;
import com.kstarrain.request.runnable.GoodsControllerRunnable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Dong Yu
 * @create: 2018-11-13 15:52
 * @description: 并发请求测试  junit不支持多线程测试，改用main方法测试
 */


public class ConcurrentRequest {

    //商品id
    private static String goodsId = "d7fbbfd87a08408ba994dea6be435111";

    //采购量
    private static int quantity = 1;


    /** 高并发抢购测试  */
    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:config/application.xml");
        GoodsController goodsController = applicationContext.getBean("goodsController", GoodsController.class);

        System.out.println("===========================================================");

        ExecutorService executor = Executors.newFixedThreadPool(1000);
        // 测试10000人同时抢购
        for (int i = 1; i <= 1000; i++) {
            executor.execute(new GoodsControllerRunnable(goodsController,"user" + i, goodsId, quantity));
        }

        executor.shutdown();

    }

}
