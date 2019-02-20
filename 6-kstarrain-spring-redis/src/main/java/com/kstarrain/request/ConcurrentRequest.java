package com.kstarrain.request;

import com.kstarrain.controller.GoodsController;
import com.kstarrain.request.runnable.GoodsControllerRunnable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: DongYu
 * @create: 2019-02-16 19:12
 * @description: 基于watch的redis秒杀(乐观锁)
*                https://blog.csdn.net/qq1013598664/article/details/70183908
 */
@Slf4j
public class ConcurrentRequest {

    //商品key
    private static String goodsKey = "concurrent:goods:iphone8";
    //商品总库存数
    private static int stock = 10;

    //单次抢购数量
    private static int quantity = 1;




    /** 高并发抢购测试  */
    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:config/application.xml");
        GoodsController goodsController = applicationContext.getBean("goodsController", GoodsController.class);

        // 初始化商品
        initGoods(applicationContext);

        System.out.println("===========================================================");

        ExecutorService executor = Executors.newFixedThreadPool(1000);
        // 测试一万人同时抢购
        for (int i = 1; i <= 10000; i++) {
            executor.execute(new GoodsControllerRunnable(goodsController,"user" + i, goodsKey, quantity));
        }
        executor.shutdown();

    }



    private static void initGoods(ApplicationContext applicationContext) {
        StringRedisTemplate stringRedisTemplate = applicationContext.getBean("stringRedisTemplate", StringRedisTemplate.class);

        //设置商品总库存
        stringRedisTemplate.opsForValue().set(goodsKey, String.valueOf(stock));
        //删除抢购成功key
        Set<String> keys = stringRedisTemplate.keys("concurrent:order:*");
        stringRedisTemplate.delete(keys);
    }

}
