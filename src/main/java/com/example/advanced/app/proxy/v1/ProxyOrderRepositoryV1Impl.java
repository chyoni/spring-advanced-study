package com.example.advanced.app.proxy.v1;

public class ProxyOrderRepositoryV1Impl implements ProxyOrderRepositoryV1 {
    @Override
    public void save(String itemId) {

        if (itemId.equals("ex")) {
            throw new IllegalStateException("예외 발생!");
        }

        sleep(1000);
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
