package com.potato.boot.config.rocketmq;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.LocalTransactionExecuter;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ProduceProxy {
    private static final Logger log = LoggerFactory.getLogger(ProduceProxy.class);
    private final String nameSrv;
    private final String groupName;
    private final String env = "";
    private DefaultMQProducer producer;

    public ProduceProxy(String nameSrv, String groupName) {
        this.nameSrv = nameSrv;
        this.groupName = groupName;

        try {
            this.producer = new DefaultMQProducer(groupName);
            this.producer.setNamesrvAddr(this.nameSrv);
            this.producer.start();
        } catch (Exception var5) {
            log.error("init rocket produce error", var5);
        }

    }

    private void preCondition(Message msg) {
        if (StringUtils.isNotEmpty(this.env)) {
            msg.setTopic(this.env + msg.getTopic());
        }
    }

    public SendResult send(Message msg) throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
        this.preCondition(msg);
        return this.producer.send(msg);
    }

    public SendResult send(Message msg, long timeout) throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
        this.preCondition(msg);
        return this.producer.send(msg, timeout);
    }

    public void send(Message msg, SendCallback sendCallback) throws MQClientException, RemotingException, InterruptedException {
        this.preCondition(msg);
        this.producer.send(msg, sendCallback);
    }

    public void send(Message msg, SendCallback sendCallback, long timeout) throws MQClientException, RemotingException, InterruptedException {
        this.preCondition(msg);
        this.producer.send(msg, sendCallback, timeout);
    }

    public SendResult send(Message msg, MessageQueue mq) throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
        this.preCondition(msg);
        return this.producer.send(msg, mq);
    }

    public SendResult send(Message msg, MessageQueue mq, long timeout) throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
        this.preCondition(msg);
        return this.producer.send(msg, mq, timeout);
    }

    public void send(Message msg, MessageQueue mq, SendCallback sendCallback) throws MQClientException, RemotingException, InterruptedException {
        this.preCondition(msg);
        this.producer.send(msg, mq, sendCallback);
    }

    public void send(Message msg, MessageQueue mq, SendCallback sendCallback, long timeout) throws MQClientException, RemotingException, InterruptedException {
        this.preCondition(msg);
        this.producer.send(msg, mq, sendCallback, timeout);
    }

    public SendResult send(Message msg, MessageQueueSelector selector, Object arg) throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
        this.preCondition(msg);
        return this.producer.send(msg, selector, arg);
    }

    public SendResult send(Message msg, MessageQueueSelector selector, Object arg, long timeout) throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
        this.preCondition(msg);
        return this.producer.send(msg, selector, arg, timeout);
    }

    public void send(Message msg, MessageQueueSelector selector, Object arg, SendCallback sendCallback) throws MQClientException, RemotingException, InterruptedException {
        this.preCondition(msg);
        this.producer.send(msg, selector, arg, sendCallback);
    }

    public void send(Message msg, MessageQueueSelector selector, Object arg, SendCallback sendCallback, long timeout) throws MQClientException, RemotingException, InterruptedException {
        this.preCondition(msg);
        this.producer.send(msg, selector, arg, sendCallback, timeout);
    }

    public TransactionSendResult sendMessageInTransaction(Message msg, LocalTransactionExecuter tranExecuter, Object arg) throws MQClientException {
        this.preCondition(msg);
        return this.producer.sendMessageInTransaction(msg, tranExecuter, arg);
    }

    public SendResult send(Collection<Message> msgs) throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
        Iterator var2 = msgs.iterator();

        while (var2.hasNext()) {
            Message msg = (Message) var2.next();
            this.preCondition(msg);
        }

        return this.producer.send(msgs);
    }

    public SendResult send(Collection<Message> msgs, long timeout) throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
        Iterator var4 = msgs.iterator();

        while (var4.hasNext()) {
            Message msg = (Message) var4.next();
            this.preCondition(msg);
        }

        return this.producer.send(msgs, timeout);
    }

    public SendResult send(Collection<Message> msgs, MessageQueue mq) throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
        Iterator var3 = msgs.iterator();

        while (var3.hasNext()) {
            Message msg = (Message) var3.next();
            this.preCondition(msg);
        }

        return this.producer.send(msgs, mq);
    }

    public SendResult send(Collection<Message> msgs, MessageQueue mq, long timeout) throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
        Iterator var5 = msgs.iterator();

        while (var5.hasNext()) {
            Message msg = (Message) var5.next();
            this.preCondition(msg);
        }

        return this.producer.send(msgs, mq, timeout);
    }

    public List<MessageQueue> fetchPublishMessageQueues(String topic) throws MQClientException {
        topic = this.env + topic;
        return this.producer.fetchPublishMessageQueues(topic);
    }

    public DefaultMQProducer getProducer() {
        return this.producer;
    }
}
