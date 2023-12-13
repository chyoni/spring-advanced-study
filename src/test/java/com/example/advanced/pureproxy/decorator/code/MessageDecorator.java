package com.example.advanced.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageDecorator implements Component {

    private Component target;

    public MessageDecorator(Component component) {
        this.target = component;
    }

    @Override
    public String operation() {
        log.info("MessageDecorator Start");

        String operation = target.operation();
        String decoResult = "****" + operation + "****";

        log.info("MessageDecorator not accepted={}, accepted={}", operation, decoResult);

        return decoResult;
    }
}
