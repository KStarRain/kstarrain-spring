package com.kstarrain;

import com.alibaba.fastjson.JSON;
import com.kstarrain.pojo.Student;
import com.kstarrain.utils.TestDataUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:config/application.xml"})
@Slf4j
public class RedisTemplateTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testCommon() {
        System.out.println("=====================================================================");
        String KEY = "key_string";

        //设置key的过期时间
        redisTemplate.expire(KEY,10,TimeUnit.SECONDS);

        //判断key是否存在
        Boolean hasKey = redisTemplate.hasKey(KEY);
        System.out.println(hasKey);
        System.out.println("=====================================================================");

        redisTemplate.delete(KEY);
    }


    @Test
    public void testString() {
        System.out.println("=====================================================================");

        String KEY = "key_string";

        //设置key-value
        redisTemplate.opsForValue().set(KEY,"貂蝉");
        System.out.println((String) redisTemplate.opsForValue().get(KEY));

        System.out.println("------------------------------");

        stringRedisTemplate.opsForValue().set(KEY,"貂蝉");
        System.out.println(stringRedisTemplate.opsForValue().get(KEY));

        System.out.println("=====================================================================");

    }


    @Test
    public void testStudent() {
        System.out.println("=====================================================================");

        String KEY = "key_student";

        redisTemplate.opsForValue().set(KEY,JSON.toJSONString(TestDataUtils.createStudent1()));
        String studentStr1 = (String) redisTemplate.opsForValue().get(KEY);
        Student student1 = JSON.parseObject(studentStr1, Student.class);

        System.out.println("------------------------------");

        stringRedisTemplate.opsForValue().set(KEY,JSON.toJSONString(TestDataUtils.createStudent2()));
        String studentStr2 = stringRedisTemplate.opsForValue().get(KEY);
        Student student2 = JSON.parseObject(studentStr2, Student.class);

        System.out.println("=====================================================================");
    }


    @Test
    public void testHashMap() {
        String KEY = "key_hashmap";

        HashMap<String, String> map = new HashMap<>();
        map.put("2018/01/01","天津");
        map.put("2018/01/02","上海");

//        redisTemplate.opsForHash().putAll(KEY,map);

        stringRedisTemplate.opsForHash().putAll(KEY,map);
    }



}
