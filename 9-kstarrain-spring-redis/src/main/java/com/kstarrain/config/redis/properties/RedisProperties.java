package com.kstarrain.config.redis.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author: DongYu
 * @create: 2019-02-19 09:07
 * @description: redis配置信息
 *
 *  1. 在类名上面使用  @PropertySource("classpath:*") 注解,*代表属性文件路径,可以指向多个配置文件路径，如果是多个配置文件,则是 @PropertySource({"classpath:*","classpath:*"....})
 *  2. 在字段上直接使用@value注解，注解内使用${core.pool.size}  core.pool.size 代表属性文件里面的key

 */
@Component
@PropertySource("classpath:properties/redis.properties")
@Data
public class RedisProperties {

    //ip地址
    @Value("${redis.hostName}")
    private String hostName;

    //端口号
    @Value("${redis.port}")
    private int port;

    //密码
    @Value("${redis.password}")
    private String password;

    //客户端超时时间单位是毫秒 默认是2000
    @Value("${redis.timeout}")
    private int timeout;

    //最大空闲数
    @Value("${redis.maxIdle}")
    private int maxIdle;

    //连接池的最大数据库连接数。设为0表示无限制,如果是jedis 2.4以后用redis.maxTotal
//    @Value("${redis.maxActive}")
//    private int maxActive;

    //控制一个pool可分配多少个jedis实例,用来替换上面的redis.maxActive,如果是jedis 2.4以后用该属性
    @Value("${redis.maxTotal}")
    private int maxTotal;

    //最大建立连接等待时间。如果超过此时间将接到异常。设为-1表示无限制。
    @Value("${redis.maxWaitMillis}")
    private int maxWaitMillis;

    //释放连接的最小空闲时间 默认1800000毫秒(30分钟)
    @Value("${redis.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    //每次释放连接的最大数目,默认3
    @Value("${redis.numTestsPerEvictionRun}")
    private int numTestsPerEvictionRun;

    //逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
    @Value("${redis.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    //是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
    @Value("${redis.testOnBorrow}")
    private Boolean testOnBorrow;

    //在空闲时检查有效性, 默认false
    @Value("${redis.testWhileIdle}")
    private Boolean testWhileIdle;

}
