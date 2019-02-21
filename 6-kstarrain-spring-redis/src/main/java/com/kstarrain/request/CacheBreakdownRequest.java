package com.kstarrain.request;

import com.kstarrain.controller.GoodsController;
import com.kstarrain.request.runnable.CacheBreakdownRunnable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: DongYu
 * @create: 2019-02-16 19:12
 * @description: 缓存击穿
 *               https://blog.csdn.net/sanyaoxu_2/article/details/79472465
 */
@Slf4j
public class CacheBreakdownRequest {

    private static String key = "all_goods";


    /** 缓存并发测试  */
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:config/application.xml");
        GoodsController goodsController = applicationContext.getBean("goodsController", GoodsController.class);

        deleteAllGoods(applicationContext);

        System.out.println("===========================================================");

        ExecutorService executor = Executors.newFixedThreadPool(1000);
        // 测试一万人同时抢购
        for (int i = 1; i <= 10000; i++) {
            executor.execute(new CacheBreakdownRunnable(goodsController,"user" + i,key));
        }
        executor.shutdown();

    }


    private static void deleteAllGoods(ApplicationContext applicationContext) {
        StringRedisTemplate stringRedisTemplate = applicationContext.getBean("stringRedisTemplate", StringRedisTemplate.class);

        stringRedisTemplate.delete(key);
    }



}
