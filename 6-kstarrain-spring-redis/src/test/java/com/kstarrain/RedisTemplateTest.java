package com.kstarrain;

import com.alibaba.fastjson.JSON;
import com.kstarrain.pojo.Student;
import com.kstarrain.utils.RedisUtils;
import com.kstarrain.utils.TestDataUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.*;
import java.util.concurrent.TimeUnit;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:config/application.xml"})
@Slf4j
public class RedisTemplateTest extends AbstractJUnit4SpringContextTests {

//    @Autowired
//    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Test
    public void testCommon() {
        System.out.println("=====================================================================");
        String KEY = "key_string";

        //设置key的过期时间
        stringRedisTemplate.expire(KEY,10,TimeUnit.SECONDS);

        //判断key是否存在
        Boolean hasKey = stringRedisTemplate.hasKey(KEY);
        System.out.println(hasKey);
        System.out.println("=====================================================================");

        stringRedisTemplate.delete(KEY);
    }


    @Test
    public void testString() {
        System.out.println("=====================================================================");

        String KEY = "key_string";

        //设置key-value
//        redisTemplate.opsForValue().set(KEY,"貂蝉");
//        System.out.println((String) redisTemplate.opsForValue().get(KEY));

        System.out.println("------------------------------");

        stringRedisTemplate.opsForValue().set(KEY,"吕布");
        System.out.println(stringRedisTemplate.opsForValue().get(KEY));

        System.out.println("=====================================================================");

    }


    @Test
    public void testStudent() {
        System.out.println("=====================================================================");

        String KEY = "key_student";

//        redisTemplate.opsForValue().set(KEY,JSON.toJSONString(TestDataUtils.createStudent1()));
//        String studentStr1 = (String) redisTemplate.opsForValue().get(KEY);
//        Student student1 = JSON.parseObject(studentStr1, Student.class);
//        System.out.println(student1);
//
//        System.out.println("------------------------------");

        stringRedisTemplate.opsForValue().set(KEY,JSON.toJSONString(TestDataUtils.createStudent2()));
        String studentStr2 = stringRedisTemplate.opsForValue().get(KEY);
        Student student2 = JSON.parseObject(studentStr2, Student.class);
        System.out.println(student2);

        System.out.println("=====================================================================");
    }

    @Test
    public void testList() {
        System.out.println("=====================================================================");

        String KEY = "key_list";

        //一个一个添加： 将值插入到列表头部
//        stringRedisTemplate.opsForList().leftPush(KEY,"吕布");
        //一个一个添加： 将值插入到列表尾部
//        stringRedisTemplate.opsForList().rightPush(KEY,"貂蝉");


        List<String> list = new ArrayList<>();
        list.add("貂蝉");
        list.add("吕布");
        stringRedisTemplate.opsForList().rightPushAll(KEY,list);


        List<String> value = stringRedisTemplate.opsForList().range(KEY, 0, -1);
        System.out.println(value);


        System.out.println("=====================================================================");
    }


    @Test
    public void testSet() {
        System.out.println("=====================================================================");

        String KEY = "key_set";

        List<String> values = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            values.add("貂蝉" + i);
        }


        long start1 = System.currentTimeMillis();
        for (String value : values) {
            stringRedisTemplate.opsForSet().add(KEY,value);
        }
        long end1 = System.currentTimeMillis();
        System.out.println("循环插入 " + values.size() + " 条数据共花费了 " +(end1-start1) +" 毫秒");



        long start2 = System.currentTimeMillis();
        stringRedisTemplate.opsForSet().add(KEY,values.toArray(new String[values.size()]));
        long end2 = System.currentTimeMillis();
        System.out.println("批量插入 " + values.size() + " 条数据共花费了 " +(end2-start2) +" 毫秒");


       /* Boolean isMember = stringRedisTemplate.opsForSet().isMember(KEY, "貂蝉");
        System.out.println(isMember);

        Set<String> value = stringRedisTemplate.opsForSet().members(KEY);
        System.out.println(value);*/

        System.out.println("=====================================================================");
    }


    @Test
    public void testSortedSet() {
        System.out.println("=====================================================================");

        String KEY = "key_sorted_set";

        stringRedisTemplate.opsForZSet().add(KEY,"吕布",1.1);
        stringRedisTemplate.opsForZSet().add(KEY,"貂蝉",1.2);

        Long rank = stringRedisTemplate.opsForZSet().rank(KEY, "吕布");
        System.out.println(rank);

        Set<String> value = stringRedisTemplate.opsForZSet().range(KEY,0,-1);
        System.out.println(value);

        Set<ZSetOperations.TypedTuple<String>> typedTuples = stringRedisTemplate.opsForZSet().rangeWithScores(KEY, 0, -1);
        if (CollectionUtils.isNotEmpty(typedTuples)){
            for (ZSetOperations.TypedTuple<String> typedTuple : typedTuples) {
                System.out.println(typedTuple.getScore() + " - " + typedTuple.getValue());
            }
        }
        System.out.println("=====================================================================");
    }


    @Test
    public void testHashMap() {
        System.out.println("=====================================================================");

        String KEY = "key_hashmap";

        HashMap<String, String> map = new HashMap<>();
        map.put("2018/01/01","天津");
        map.put("2018/01/02","上海");

        stringRedisTemplate.opsForHash().putAll(KEY,map);

        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(KEY);
        System.out.println(entries);
        System.out.println("=====================================================================");
    }



    @Test
    public void testSyncSet() {
        System.out.println("=====================================================================");

        String KEY = "key_set";

        Collection<String> oldValue = new ArrayList<>();
        oldValue.add("貂蝉");
        oldValue.add("妲己");
        stringRedisTemplate.opsForSet().add(KEY,oldValue.toArray(new String[oldValue.size()]));

        Collection<String> newValue = new ArrayList<>();
        newValue.add("张飞");
        newValue.add("吕布");

        RedisUtils.syncSet(KEY,newValue,-1L);

        System.out.println("=====================================================================");
    }


    /** 测试redis管道 */
    @Test
    public void testPipeline() {
        System.out.println("=====================================================================");

        String KEY = "key_set";

        List<String> values = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            values.add("貂蝉" + i);
        }

        SetOperations<String, String> setOperations = stringRedisTemplate.opsForSet();
        RedisSerializer<String> stringSerializer = stringRedisTemplate.getStringSerializer();



        /** TCP协议(多connection)
         *  对于 spring-data-redis 而言，每次操作(例如:get,set,add)都有会从连接池中获取connection
         *  跟踪add、set等源代码，发现会执行RedisTemplate类中execute()方法,
         *  发现 RedisCallback中doInRedis获取的RedisConnection每次都是新的，所以才导致不是一个连接在操作
         * */
        long start1 = System.currentTimeMillis();
        for (String value : values) {
            setOperations.add(KEY,value);
        }
        long end1 = System.currentTimeMillis();
        System.out.println("TCP协议(多connection)                                 插入 " + values.size() + " 条数据共花费了 " +(end1-start1) +" 毫秒");





        /** TCP协议(单connection)-SessionCallback             对于手写 execute 而言, SessionCallback一直使用同一个connection */
        long start2 = System.currentTimeMillis();
        stringRedisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public  Object execute(RedisOperations redisOperations) throws DataAccessException {
                SetOperations kvSetOperations = redisOperations.opsForSet();
                for (String value : values) {
                    kvSetOperations.add(KEY, value);
                }
                return null;
            }
        });
        long end2 = System.currentTimeMillis();
        System.out.println("TCP协议(单connection)      -SessionCallback           插入 " + values.size() + " 条数据共花费了 " +(end2-start2) +" 毫秒");





        /** TCP协议(单connection)-RedisCallback(手动序列化)    对于手写 execute 而言, RedisCallback一直使用同一个connection,这种方法要比上面那种快一点，因为是直接操作connection，但需要手动序列化key-value */
        long start3 = System.currentTimeMillis();
        stringRedisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                for (String value : values) {
                    connection.sAdd(stringSerializer.serialize(KEY), stringSerializer.serialize(value));
                }
                return null;
            }
        });
        long end3 = System.currentTimeMillis();
        System.out.println("TCP协议(单connection)      -RedisCallback(序列化键值)   插入 "  + values.size() + " 条数据共花费了 " +(end3-start3) +" 毫秒");





        /**  管道技术(手写open-close代码)-RedisCallback(序列化键值) */
//        long start4 = System.currentTimeMillis();
//        stringRedisTemplate.execute(new RedisCallback<Object>() {
//            @Override
//            public Object doInRedis(RedisConnection connection) throws DataAccessException {
//                // 开启管道
//                connection.openPipeline();
//
//                try {
//                    for (String value : values) {
//                        connection.sAdd(stringSerializer.serialize(KEY), stringSerializer.serialize(value));
//                    }
//                } finally {
//                    // 关闭管道
//                    connection.closePipeline();
//                }
//                return null;
//            }
//        });
//        long end4 = System.currentTimeMillis();
//        System.out.println("管道技术(手写open-close代码)-RedisCallback(序列化键值)   插入 " + values.size() + " 条数据共花费了 " +(end4-start4) +" 毫秒");




        /**  管道技术(executePipelined) --SessionCallback    executePipelined中包含了开启管道和关闭管道的方法，不用手写 */
        long start5 = System.currentTimeMillis();
        stringRedisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public  Object execute(RedisOperations redisOperations) throws DataAccessException {
                SetOperations kvSetOperations = redisOperations.opsForSet();
                for (String value : values) {
                    kvSetOperations.add(KEY, value);
                }
                return null;
            }
        });
        long end5 = System.currentTimeMillis();
        System.out.println("管道技术(executePipelined) -SessionCallback           插入 " + values.size() + " 条数据共花费了 " +(end5-start5) +" 毫秒");





        /**  管道技术(executePipelined) -RedisCallback(序列化键值)  executePipelined中包含了开启管道和关闭管道的方法，不用手写 */
        long start6 = System.currentTimeMillis();
        stringRedisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                for (String value : values) {
                    connection.sAdd(stringSerializer.serialize(KEY), stringSerializer.serialize(value));
                }
                return null;
            }
        });
        long end6 = System.currentTimeMillis();
        System.out.println("管道技术(executePipelined) -RedisCallback(序列化键值)   插入 " + values.size() + " 条数据共花费了 " +(end6-start6) +" 毫秒");




    }


    /**
     * 事务操作 stringRedisTemplate.setEnableTransactionSupport(false)
     * 对于 spring-data-redis 而言，每次操作(例如:get,set,add)都有会从连接池中获取connection
     * 跟踪multi、delete、set等源代码，发现会执行RedisTemplate类中execute()方法
     * 发现 RedisCallback中doInRedis获取的RedisConnection每次都是新的，所以才导致不是一个连接在操作
     *
     * https://www.cnblogs.com/zxf330301/p/6889202.html
     *
     * 如果设置 stringRedisTemplate.setEnableTransactionSupport(true)开启事务，会一直使用一个连接，但不自动释放链接;
     * 需要手动释放链接 RedisConnectionUtils.unbindConnection(stringRedisTemplate.getConnectionFactory());
     *
     */
    @Test
    @Transactional
    public void testTransaction_error(){
        System.out.println("=====================================================================");
        String KEY = "key_set";

        try {
            //该命令用户高并发时的乐观锁 监视key，当事务执行之前这个key发生了改变，事务会被中断，事务exec返回结果为null
            stringRedisTemplate.watch(KEY);

//            stringRedisTemplate.multi();

            SetOperations setOperations = stringRedisTemplate.opsForSet();

            setOperations.add(KEY,"貂蝉");

//        int a = 5/0;

            setOperations.add(KEY,"吕布");

//            List exec = stringRedisTemplate.exec();
        } finally {
            /** 手动释放链接 */
            RedisConnectionUtils.unbindConnection(stringRedisTemplate.getConnectionFactory());
        }
        System.out.println("=====================================================================");
    }


    /**
     * 测试redis事务
     */
    @Test
    public void testTransaction01(){
        System.out.println("=====================================================================");

        String KEY = "key_set";

        stringRedisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public  Object execute(RedisOperations redisOperations) throws DataAccessException {

                SetOperations setOperations = redisOperations.opsForSet();

                //该命令用户高并发时的乐观锁 监视key，当事务执行之前这个key发生了改变，事务会被中断，事务exec返回结果为null
                redisOperations.watch(KEY);

                //开启事务
                redisOperations.multi();

                setOperations.add(KEY, "貂蝉");

//                int a = 5/0;

                setOperations.add(KEY, "吕布");

                //提交事务
                List<Object> exec = redisOperations.exec();
                if (exec == null){
                    System.out.println("事务提交失败，原因为提交前key被其他用户修改");
                }
                return null;
            }
        });
        System.out.println("=====================================================================");
    }


    @Test
    public void testTransaction02(){
        System.out.println("=====================================================================");

        String KEY = "key_set";

        stringRedisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public  Object execute(RedisOperations redisOperations) throws DataAccessException {

                SetOperations setOperations = redisOperations.opsForSet();

                //该命令用户高并发时的乐观锁 监视key，当事务执行之前这个key发生了改变，事务会被中断，事务exec返回结果为null
                redisOperations.watch(KEY);

                //开启事务
                redisOperations.multi();

                setOperations.add(KEY, "吕布");

                //提交事务
                List<Object> exec = redisOperations.exec();
                if (exec == null){
                    System.out.println("事务提交失败，原因为提交前key被其他用户修改");
                }
                return null;
            }
        });
        System.out.println("=====================================================================");
    }


    /**
     * 日志等级调为debug发现 如果stringRedisTemplate中设置开启事务 stringRedisTemplate.setEnableTransactionSupport(true)
     * 那么使用RedisCallback时不会释放链接，
     * 但是使用SessionCallback没问题
     * https://blog.csdn.net/yulio1234/article/details/76417688
     */
    @Test
    public void testTransaction03(){
        System.out.println("=====================================================================");

        String KEY = "key_set";

        RedisSerializer<String> stringSerializer = stringRedisTemplate.getStringSerializer();

        stringRedisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {

                //该命令用户高并发时的乐观锁 监视key，当事务执行之前这个key发生了改变，事务会被中断，事务exec返回结果为null
                connection.watch(stringSerializer.serialize(KEY));

                //开启事务
                connection.multi();

                connection.sAdd(stringSerializer.serialize(KEY), stringSerializer.serialize("貂蝉"));

//                int a = 5/0;

                connection.sAdd(stringSerializer.serialize(KEY), stringSerializer.serialize("吕布"));

                //提交事务
                List<Object> exec = connection.exec();
                if (exec == null){
                    System.out.println("事务提交失败，原因为提交前key被其他用户修改");
                }
                return null;
            }
        });
        System.out.println("=====================================================================");
    }


}
