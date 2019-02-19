package com.kstarrain.config.redis;

import com.kstarrain.config.redis.properties.RedisProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author: DongYu
 * @create: 2019-02-19 09:02
 * @description: Spring整合redis配置
 */
@Configuration
public class RedisConfig {

    @Autowired
    private RedisProperties redisProperties;

    /** redis连接池配置（使用jedis驱动） */
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //最大空闲数
        jedisPoolConfig.setMaxIdle(redisProperties.getMaxIdle());
        //连接池的最大数据库连接数
        jedisPoolConfig.setMaxTotal(redisProperties.getMaxTotal());
        //最大建立连接等待时间
        jedisPoolConfig.setMaxWaitMillis(redisProperties.getMaxWaitMillis());
        //释放连接的最小空闲时间
        jedisPoolConfig.setMinEvictableIdleTimeMillis(redisProperties.getMinEvictableIdleTimeMillis());
        //每次释放连接的最大数目
        jedisPoolConfig.setNumTestsPerEvictionRun(redisProperties.getNumTestsPerEvictionRun());
        //逐出扫描的时间间隔(毫秒)
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(redisProperties.getTimeBetweenEvictionRunsMillis());
        //是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
        jedisPoolConfig.setTestOnBorrow(redisProperties.getTestOnBorrow());
        //在空闲时检查有效性,
        jedisPoolConfig.setTestWhileIdle(redisProperties.getTestWhileIdle());
        return jedisPoolConfig;
    }


    /** redis连接工厂 redis和spring的整合 */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        //连接池信息
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
        //IP地址
        jedisConnectionFactory.setHostName(redisProperties.getHostName());
        //端口号
        jedisConnectionFactory.setPort(redisProperties.getPort());
        //密码
        if (StringUtils.isNotBlank(redisProperties.getPassword())){
            jedisConnectionFactory.setPassword(redisProperties.getPassword());
        }
        //客户端超时时间单位是毫秒
        jedisConnectionFactory.setTimeout(redisProperties.getTimeout());
        return jedisConnectionFactory;
    }


    /** String序列化策略 */
    @Bean
    public StringRedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer();
    }



    /**
     *  RedisTemplate默认采用的是JDK的序列化策略，保存的key和value都是采用此策略序列化保存的。
     *  现将JDK的序列化策略全部改为String序列化策略
     */
    @Bean
    public RedisTemplate redisTemplate(JedisConnectionFactory jedisConnectionFactory,StringRedisSerializer stringRedisSerializer) {
        RedisTemplate redisTemplate = new RedisTemplate();
        //redis连接工厂
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        //设置key 采用String序列化策略
        redisTemplate.setKeySerializer(stringRedisSerializer);
        //设置value 采用String序列化策略
        redisTemplate.setValueSerializer(stringRedisSerializer);
        //设置在hash数据结构中，hash-key 采用String序列化策略
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        //设置在hash数据结构中，hash-value 采用String序列化策略
        redisTemplate.setHashValueSerializer(stringRedisSerializer);
        return redisTemplate;
    }


    @Bean
    public StringRedisTemplate stringRedisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        //redis连接工厂
        stringRedisTemplate.setConnectionFactory(jedisConnectionFactory);
        //日志等级调为debug发现 如果redisTemplate中设置开启事务，调用RedisCallback时不会释放链接
//        stringRedisTemplate.setEnableTransactionSupport(true);
        return stringRedisTemplate;
    }





}
