package com.example.advanced.app.proxy.v1;

public class ProxyOrderServiceV1Impl implements ProxyOrderServiceV1 {

    private final ProxyOrderRepositoryV1 orderRepository;

    public ProxyOrderServiceV1Impl(ProxyOrderRepositoryV1 orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void orderItem(String itemId) {
        orderRepository.save(itemId);
    }
}
