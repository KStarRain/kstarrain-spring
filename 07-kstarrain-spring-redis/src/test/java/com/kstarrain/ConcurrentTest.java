package com.kstarrain;

import com.kstarrain.utils.DistributedLockUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:config/application.xml"})
@Slf4j
public class ConcurrentTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testDistributedLock01() {
        System.out.println("===========================================================");
        String KEY = "888";
        String requestId = UUID.randomUUID().toString();
        //是否获取到了锁
        boolean lockable = false;

        try {

            lockable = DistributedLockUtils.tryLock(KEY, requestId, 10000L, TimeUnit.SECONDS);

            if (lockable){
                System.out.println("获得锁，执行业务逻辑");
            }else {
                System.out.println("未获得锁，请重试");
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            if (lockable){DistributedLockUtils.releaseLock(KEY,requestId);}
        }
        System.out.println("===========================================================");
    }

}
