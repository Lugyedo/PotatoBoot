package com.potato.boot.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMQReceiver {
    @RabbitListener(queues = "TestTopic")
    public void processTestTopic(String message) {
        log.info("RabbitMQ Receiver TestTopic message={}", message);
    }

    @RabbitListener(queues = "ItsmTopic")
    public void processItsmTopic(String message) {
        log.info("RabbitMQ Receiver ItsmTopic message={}", message);
    }
}
