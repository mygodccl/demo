package com.example.demo.util.springEvent;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ConditionEventListener {

    @EventListener(condition = "#event.message eq '123'")
    public void onApplicationEvent(MessageEvent event) {
        System.out.println(event.getMessage());
    }
}
