package com.example.demo.util.springEvent;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EventListener implements ApplicationListener<MessageEvent> {

    @Async
    @Override
    public void onApplicationEvent(MessageEvent event) {
        System.out.println(event.getMessage());
    }
}
