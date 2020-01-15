package com.kstarrain.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author: DongYu
 * @create: 2019-05-13 18:05
 * @description: 基于redis的分布式锁
 */
public class DistributedLockUtils {

    private static StringRedisTemplate stringRedisTemplate;

    /** 解锁的lua脚本 */
    public static final String UNLOCK_LUA;

    /** SET IF NOT EXIST 等效于 SETNX */
    public static final String NX = "NX";

    /** 以毫秒为单位设置 key 的过期时间 */
    public static final String PX = "PX";
    /** 以秒为单位设置 key 的过期时间 */
    public static final String EX = "EX";


    private static final String LOCK_SUCCESS = "OK";
    private static final Long RELEASE_SUCCESS = 1L;

    public static final String LOCK_PREFIX = "DISTRIBUTED_LOCK_";


    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call('get',KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call('del',KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();
    }

    public static void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        DistributedLockUtils.stringRedisTemplate = stringRedisTemplate;
    }



    /**
     * 尝试获取分布式锁
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 过期时间
     * @param unit 时间单元
     * @return 是否获取成功
     */
    public static boolean tryLock(String lockKey, String requestId, Long expireTime, TimeUnit unit) {

        Assert.isTrue(StringUtils.isNotBlank(lockKey), "key is not null");
        return stringRedisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                Object nativeConnection = connection.getNativeConnection();
                String result = null;
                if (nativeConnection instanceof JedisCommands) {

                    long timeOut;
                    String redisUnit;

                    if (TimeUnit.MILLISECONDS.equals(unit)) {
                        timeOut = expireTime;
                        redisUnit = PX;
                    } else {
                        timeOut = TimeoutUtils.toSeconds(expireTime, unit);
                        redisUnit = EX;
                    }

                    /*  第一个为key，使用key来当锁，因为key是唯一的。
                        第二个为value，传的是requestId，有人可能不明白，有key作为锁不就够了吗，为什么还要用到value？
                             原因就是可靠性时，分布式锁要满足第四个条件解铃还须系铃人，通过给value赋值为requestId，
                             就知道这把锁是哪个请求加的了，在解锁的时候就可以有依据。requestId可以使用UUID.randomUUID().toString()方法生成。
                        第三个为NX，意思是SET IF NOT EXIST，即当key不存在时，我们进行set操作；若key已经存在，则不做任何操作；
                        第四个为EX/PX，这个参数目前写死为为时间单位（秒），意思是要给这个key加一个过期的设置，具体时间由第五个参数决定。
                        第五个为time，与第四个参数相呼应，代表key的过期时间。
                    */
                    result = ((JedisCommands) nativeConnection).set(LOCK_PREFIX + lockKey, requestId, NX, redisUnit, timeOut);

                }
                return LOCK_SUCCESS.equalsIgnoreCase(result);
            }
        });


    }


    /**
     * 解锁
     * 不使用 DEL 命令来释放锁，而是发送一个 Lua 脚本，这个脚本只在客户端传入的值和键的口令串相匹配时，才对键进行删除。
     */
    public static boolean releaseLock(String lockKey, String requestId) {

         return stringRedisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                Object nativeConnection = connection.getNativeConnection();
                Object result = 0L;

                // 集群模式
                if (nativeConnection instanceof JedisCluster) {
                    result = ((JedisCluster) nativeConnection).eval(UNLOCK_LUA, Collections.singletonList(LOCK_PREFIX + lockKey), Collections.singletonList(requestId));
                }

                // 单机模式
                else if (nativeConnection instanceof Jedis) {
                    result = ((Jedis) nativeConnection).eval(UNLOCK_LUA, Collections.singletonList(LOCK_PREFIX + lockKey), Collections.singletonList(requestId));
                }
                return RELEASE_SUCCESS.equals(result);
            }
        });

    }

}
