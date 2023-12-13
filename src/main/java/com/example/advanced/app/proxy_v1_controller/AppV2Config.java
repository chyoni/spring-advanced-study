package com.example.advanced.app.proxy_v1_controller;

import com.example.advanced.app.proxy.v1.*;
import com.example.advanced.app.proxy.v2.OrderControllerV2;
import com.example.advanced.app.proxy.v2.OrderRepositoryV2;
import com.example.advanced.app.proxy.v2.OrderServiceV2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppV2Config {

    @Bean
    public OrderControllerV2 orderControllerV2() {
        return new OrderControllerV2(orderServiceV2());
    }

    @Bean
    public OrderServiceV2 orderServiceV2() {
        return new OrderServiceV2(orderRepositoryV2());
    }

    @Bean
    public OrderRepositoryV2 orderRepositoryV2() {
        return new OrderRepositoryV2();
    }
}
