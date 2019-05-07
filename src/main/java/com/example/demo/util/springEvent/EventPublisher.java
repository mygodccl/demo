package com.example.demo.util.springEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {
    @Autowired
    private ApplicationEventPublisher publisher;

    public void publisher(String message) {
        publisher.publishEvent(new MessageEvent(new Object(), message));
    }
}
