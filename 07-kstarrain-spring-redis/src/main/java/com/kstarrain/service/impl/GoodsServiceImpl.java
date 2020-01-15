package com.kstarrain.service.impl;

import com.alibaba.fastjson.JSON;
import com.kstarrain.constants.BusinessErrorCode;
import com.kstarrain.exception.BusinessException;
import com.kstarrain.pojo.Goods;
import com.kstarrain.service.IGoodsService;
import com.kstarrain.utils.DistributedLockUtils;
import com.kstarrain.utils.ThreadLocalUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author: DongYu
 * @create: 2019-02-16 21:43
 * @description:
 */
@Service
public class GoodsServiceImpl implements IGoodsService {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void reduceStockByKey(String buyerId, String goodsKey, int quantity){

        if (quantity <= 0){throw new BusinessException(BusinessErrorCode.BUSINESS001);}

        stringRedisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public  Object execute(RedisOperations redisOperations) throws DataAccessException {

                reduceStockByKey_redis(buyerId,goodsKey,quantity,redisOperations);
                return null;
            }

        });

    }

    private void reduceStockByKey_redis(String buyerId, String goodsKey, int quantity, RedisOperations redisOperations) {

        if (!redisOperations.hasKey(goodsKey)){throw new BusinessException(BusinessErrorCode.BUSINESS002);}

        //监视一个key，当事务执行之前这个key发生了改变，事务会被中断，事务exec返回结果为null
        redisOperations.watch(goodsKey);

        ValueOperations valueOperations = redisOperations.opsForValue();

        //获取商品剩余存货量
        int stock = Integer.parseInt((String) valueOperations.get(goodsKey));

        if (stock > 0) {

            if (quantity > stock){throw new BusinessException(BusinessErrorCode.BUSINESS003);}

            // 开启事务
            redisOperations.multi();

            /** 减库存 */
            valueOperations.set(goodsKey,String.valueOf(stock - quantity));

            // 提交事务，如果此时监视的key被改动了，则返回null
            List exec = redisOperations.exec();

            if (exec != null) {//抢购成功

                /** 创建订单 */
                valueOperations.set("concurrent:order:" + buyerId, "买家" + buyerId + "抢购到了" + quantity + "件商品");
            } else {
                throw new BusinessException(BusinessErrorCode.BUSINESS004);
            }

        } else if (stock == 0) {
            throw new BusinessException(BusinessErrorCode.BUSINESS005);
        } else if (stock < 0) {
            throw new BusinessException(BusinessErrorCode.BUSINESS000);
        }
    }



    @Override
    public List<Goods> findAllGoods(String key) throws InterruptedException {

        //从缓存获取数据
        List<Goods> result = JSON.parseArray(stringRedisTemplate.opsForValue().get(key), Goods.class);

        if (CollectionUtils.isEmpty(result)){

            String requestId = UUID.randomUUID().toString();

            /**分布式锁*/
            if (DistributedLockUtils.tryLock(key,requestId,5L, TimeUnit.SECONDS)){

                try {
                    //从数据库取数据
                    result = this.getAllGoodsFromDB();

                    //将数据存入缓存
                    stringRedisTemplate.opsForValue().set(key,JSON.toJSONString(result));
                } finally {
                    /**释放锁*/
                    DistributedLockUtils.releaseLock(key,requestId);
                }
            }else {
                //再次从缓存中查询
                result = JSON.parseArray(stringRedisTemplate.opsForValue().get(key), Goods.class);

                //如果缓存中还没有数据，休眠一会递归查询
                if (CollectionUtils.isEmpty(result)){
                    Thread.sleep(2000l);
                    result = findAllGoods(key);
                }
            }
        }else{
            System.out.println(ThreadLocalUtils.getValue("USER_ID",String.class) + " ---> 查询缓存");
        }
        return result;

    }




    //模仿从数据库取数据，用时1秒
    private List<Goods> getAllGoodsFromDB() throws InterruptedException {

        Thread.sleep(1000L);

        List<Goods> allGoods = new ArrayList<>();

        Goods goods1 = new Goods();
        goods1.setGoodsId("d7fbbfd87a08408ba994dea6be435111");
        goods1.setGoodsName("iphone8");
        goods1.setStock(10);
        allGoods.add(goods1);

        Goods goods2 = new Goods();
        goods2.setGoodsId("d7fbbfd87a08408ba994dea6be435222");
        goods2.setGoodsName("xiaomi");
        goods2.setStock(10);
        allGoods.add(goods2);

        System.out.println(ThreadLocalUtils.getValue("USER_ID",String.class) + " ---> 查询数据库");

        return allGoods;

    }
}