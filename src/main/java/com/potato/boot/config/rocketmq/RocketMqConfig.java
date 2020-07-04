package com.potato.boot.config.rocketmq;

import com.google.common.collect.Maps;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Map;

//@SpringBootConfiguration
//@ConfigurationProperties("rocket.mq")
public class RocketMqConfig {
    /**
     * 分组名
     */
    private String groupName;
    /**
     * 服务地址
     */
    private String nameSrv;

    @Bean
    public ConsumerProxy consumerProxy() {
        Map<String, ConsumerMessage> retMap = Maps.newHashMap();
        // 消费队列以及对应的消费者服务
        //retMap.put("OrderDiffQueue", orderDiffMessageReceive);
        //retMap.put("TnsPartnerMessagePushQueue", partnerMessagePushReceiver);
        return new ConsumerProxy(retMap, nameSrv, groupName);
    }

    @Bean
    public ProduceProxy produceProxy() {
        return new ProduceProxy(nameSrv, groupName);
    }
}
