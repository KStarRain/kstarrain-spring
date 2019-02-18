package com.kstarrain.config;

import com.kstarrain.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author: Dong Yu
 * @create: 2018-10-12 14:12
 * @description: 手动注入dao配置
 */

@Configuration
public class RedisUtilsConfig{

    @Autowired
    public RedisUtilsConfig(StringRedisTemplate stringRedisTemplate) {
        RedisUtils.setStringRedisTemplate(stringRedisTemplate);
    }


}
