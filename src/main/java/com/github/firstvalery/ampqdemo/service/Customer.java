package com.github.firstvalery.ampqdemo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Customer {
    @RabbitListener(queues = "myQueue")
    public void receive(Message<?> message) {
        log.info("Message read from myQueue : {}", message);
        Object payload = message.getPayload();
        log.info("Payload : {}", payload);
    }
}

