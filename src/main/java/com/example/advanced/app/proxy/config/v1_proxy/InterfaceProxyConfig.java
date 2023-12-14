/*
package com.example.advanced.app.proxy.config.v1_proxy;

import com.example.advanced.LogTraceConfig;
import com.example.advanced.app.proxy_v1_controller.OrderControllerV1;
import com.example.advanced.app.proxy_v1_controller.OrderControllerV1Impl;
import com.example.advanced.app.proxy.config.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import com.example.advanced.app.proxy.config.v1_proxy.interface_proxy.OrderRepositoryInterfaceProxy;
import com.example.advanced.app.proxy.config.v1_proxy.interface_proxy.OrderServiceInterfaceProxy;
import com.example.advanced.app.proxy.v1.*;
import com.example.advanced.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(LogTraceConfig.class)
@Configuration
public class InterfaceProxyConfig {

    @Bean
    public OrderServiceV1 orderService(LogTrace logTrace) {
        OrderServiceV1Impl serviceImpl = new OrderServiceV1Impl(orderRepository(logTrace));

        return new OrderServiceInterfaceProxy(serviceImpl, logTrace);
    }

    @Bean
    public OrderControllerV1 orderController(LogTrace logTrace) {
        OrderControllerV1Impl controllerImpl = new OrderControllerV1Impl(orderService(logTrace));

        return new OrderControllerInterfaceProxy(controllerImpl, logTrace);
    }

    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace logTrace) {
        OrderRepositoryV1Impl repositoryImpl = new OrderRepositoryV1Impl();

        return new OrderRepositoryInterfaceProxy(repositoryImpl, logTrace);
    }
}
*/
