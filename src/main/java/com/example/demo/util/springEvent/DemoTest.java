package com.example.demo.util.springEvent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@EnableAsync
public class DemoTest {
    @Autowired
    private EventPublisher eventPublisher;

    @Test
    public void test() {
        eventPublisher.publisher("123");
    }
}
