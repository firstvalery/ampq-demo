package com.github.firstvalery.ampqdemo.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class Producer {
    private final RabbitTemplate rabbitTemplate;
    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    public Producer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        scheduledExecutorService.scheduleWithFixedDelay(() -> send(Instant.now()), 0, 50, TimeUnit.MILLISECONDS);
    }


    private void send(Object obj) {
        rabbitTemplate.convertAndSend("myQueue", obj);
    }


    @PreDestroy
    public void stop() {
        scheduledExecutorService.shutdown();
        try {
            if (!scheduledExecutorService.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                scheduledExecutorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduledExecutorService.shutdownNow();
        }
    }
}


