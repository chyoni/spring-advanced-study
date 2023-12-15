/*
package com.example.advanced.app.proxy.config.v1_proxy.v2_dynamicproxy;

import com.example.advanced.app.proxy.config.v1_proxy.v2_dynamicproxy.handler.LogTraceBasicHandler;
import com.example.advanced.app.proxy.v1.OrderRepositoryV1;
import com.example.advanced.app.proxy.v1.OrderRepositoryV1Impl;
import com.example.advanced.app.proxy.v1.OrderServiceV1;
import com.example.advanced.app.proxy.v1.OrderServiceV1Impl;
import com.example.advanced.app.proxy_v1_controller.OrderControllerV1;
import com.example.advanced.app.proxy_v1_controller.OrderControllerV1Impl;
import com.example.advanced.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

@Configuration
public class DynamicProxyBasicConfig {

    @Bean
    public OrderControllerV1 orderControllerV1(LogTrace logTrace) {
        OrderControllerV1 orderControllerV1 = new OrderControllerV1Impl(orderServiceV1(logTrace));

        return (OrderControllerV1) Proxy.newProxyInstance(
                orderControllerV1.getClass().getClassLoader(),
                new Class[]{OrderControllerV1.class},
                new LogTraceBasicHandler(orderControllerV1, logTrace));
    }

    @Bean
    public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
        OrderServiceV1 orderServiceV1 = new OrderServiceV1Impl(orderRepositoryV1(logTrace));

        return (OrderServiceV1) Proxy.newProxyInstance(
                orderServiceV1.getClass().getClassLoader(),
                new Class[]{OrderServiceV1.class},
                new LogTraceBasicHandler(orderServiceV1, logTrace));
    }

    @Bean
    public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
        OrderRepositoryV1 orderRepository = new OrderRepositoryV1Impl();

        return (OrderRepositoryV1) Proxy.newProxyInstance(
                orderRepository.getClass().getClassLoader(),
                new Class[]{OrderRepositoryV1.class},
                new LogTraceBasicHandler(orderRepository, logTrace));
    }


}
*/
