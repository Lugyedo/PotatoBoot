package com.potato.boot.config.rocketmq;

import org.apache.rocketmq.common.message.MessageExt;

public interface ConsumerMessage {
    boolean receive(MessageExt var1);
}
