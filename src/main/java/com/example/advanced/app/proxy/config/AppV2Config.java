package com.example.advanced.app.proxy.config;

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
        return new OrderControllerV2(orderService());
    }

    @Bean
    public OrderServiceV2 orderService() {
        return new OrderServiceV2(orderRepository());
    }

    @Bean
    public OrderRepositoryV2 orderRepository() {
        return new OrderRepositoryV2();
    }
}
