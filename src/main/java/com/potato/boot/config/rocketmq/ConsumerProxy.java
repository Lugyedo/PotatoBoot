package com.potato.boot.config.rocketmq;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ConsumerProxy {
    private static final Logger log = LoggerFactory.getLogger(ConsumerProxy.class);

    private static final int DEFAULT_RETRY = 5;
    private static final int CORE_THREAD_SIZE = 5;
    private static final int MAX_THREAD_SIZE = 20;
    private final Map<String, ConsumerMessage> topics;
    private final String namesrv;
    private Map<String, String> topicTags;
    private DefaultMQPushConsumer consumer;
    private String groupName;

    public ConsumerProxy(Map<String, ConsumerMessage> topics, String namesrv, String groupName) {
        this.topics = topics;
        this.namesrv = namesrv;
        this.groupName = groupName;
        this.initConsumer(20, 5, (MessageModel) null, 0L, 5);
    }

    private MessageListenerConcurrently genMessageListener() {
        return new MessageListenerConcurrently() {
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (int i = 0; i < msgs.size(); ++i) {
                    ConsumerMessage consumerMessage = (ConsumerMessage) ConsumerProxy.this.topics.get(((MessageExt) msgs.get(i)).getTopic());
                    if (consumerMessage == null) {
                        ConsumerProxy.log.error("no topic={} for service", ((MessageExt) msgs.get(i)).getTopic());
                    }

                    try {
                        if (!consumerMessage.receive((MessageExt) msgs.get(i))) {
                            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                        }
                    } catch (Exception var6) {
                        ConsumerProxy.log.error("consumer message error", var6);
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        };
    }

    private void initConsumerCommon(int maxThreadSize, int retryTime, MessageModel messageModel, long consumeTimeout, int coreThreadSize) {
        this.consumer = new DefaultMQPushConsumer(this.groupName);
        this.consumer.setNamesrvAddr(this.namesrv);
        this.consumer.setConsumeThreadMax(maxThreadSize);
        this.consumer.setConsumeThreadMin(coreThreadSize);
        this.consumer.setMaxReconsumeTimes(retryTime);
        this.consumer.setPullThresholdForQueue(50);
        if (messageModel != null) {
            this.consumer.setMessageModel(messageModel);
        }

        if (consumeTimeout > 0L) {
            this.consumer.setConsumeTimeout(consumeTimeout);
        }

        this.consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
    }

    private void initConsumer(int maxThreadSize, int retryTime, MessageModel messageModel, long consumeTimeout, int coreThreadSize) {
        if (this.topics == null) {
            log.error("topics is null");
        } else if (StringUtils.isEmpty(this.namesrv)) {
            log.error("namesrv is null");
        } else {
            this.initConsumerCommon(maxThreadSize, retryTime, messageModel, consumeTimeout, coreThreadSize);
            Iterator iterator = this.topics.keySet().iterator();

            try {
                String topic;
                String tags;
                for (; iterator.hasNext(); this.consumer.subscribe(topic, tags)) {
                    topic = (String) iterator.next();
                    tags = "*";
                    if (this.topicTags != null && StringUtils.isNotBlank((CharSequence) this.topicTags.get(topic))) {
                        tags = (String) this.topicTags.get(topic);
                    }
                }

                this.consumer.registerMessageListener(this.genMessageListener());
                this.consumer.start();
            } catch (Exception var10) {
                log.error("init the consumer error", var10);
            }

        }
    }

    public DefaultMQPushConsumer getConsumer() {
        return this.consumer;
    }
}
