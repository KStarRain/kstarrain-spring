package com.kstarrain.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * @author: Dong Yu
 * @create: 2019-09-18 08:40
 * @description: 对于redis的key失效监听器 参考文档 https://www.jianshu.com/p/f5f81b2b2005
 */
@Slf4j
@Component
public class RedisKeyExpiredListener extends KeyExpirationEventMessageListener {

    public RedisKeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }


    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 用户做自己的业务处理即可,注意message.toString()可以获取失效的key 或者 new String(message.getBody())
        String expiredKey = message.toString();

        log.info("取得订阅的消息后 channel:{}, message:{}", new String(message.getChannel()), expiredKey);

        if (expiredKey.startsWith("LISTENER_")){
            log.info("处理 key：{} 业务逻辑", expiredKey);
        }
    }


}
