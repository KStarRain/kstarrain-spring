package com.kstarrain.service.impl;

import com.kstarrain.constant.BusinessErrorCode;
import com.kstarrain.exception.BusinessException;
import com.kstarrain.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.List;

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
}