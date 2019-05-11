package com.example.demo.util.springEvent;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class MessageEvent extends ApplicationEvent {
    private static final long serialVersionUID = -1199926901772916035L;
    private String message;

    public MessageEvent(Object object, String message) {
        super(object);
        this.message = message;
    }
}
