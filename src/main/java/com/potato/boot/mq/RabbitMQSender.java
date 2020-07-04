package com.potato.boot.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMQSender {
    @Autowired
    private AmqpTemplate itsmRabbitTemplate;

    public String send(String topic, String message) {
        log.info("RabbitMQ send topic={} message={}", topic, message);
        itsmRabbitTemplate.convertAndSend(topic, message);
        return "OK";
    }
}
