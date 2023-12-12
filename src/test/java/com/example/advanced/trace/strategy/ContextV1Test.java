package com.example.advanced.trace.strategy;

import com.example.advanced.trace.strategy.code.strategy.ContextV1;
import com.example.advanced.trace.strategy.code.strategy.Strategy;
import com.example.advanced.trace.strategy.code.strategy.StrategyLogic1;
import com.example.advanced.trace.strategy.code.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;


@Slf4j
public class ContextV1Test {

    @Test
    void strategyV0() {
        logic1();
        logic2();
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();

        log.info("비즈니스 로직1 실행");

        long endTime = System.currentTimeMillis();

        long resultTime = endTime - startTime;

        log.info("resultTime={}", resultTime);
    }

    private void logic2() {
        long startTime = System.currentTimeMillis();

        log.info("비즈니스 로직2 실행");

        long endTime = System.currentTimeMillis();

        long resultTime = endTime - startTime;

        log.info("resultTime={}", resultTime);
    }

    /**
     * 전략 패턴 사용
     * */
    @Test
    void strategyV1() {
        StrategyLogic1 strategyLogic1 = new StrategyLogic1();
        ContextV1 context1 = new ContextV1(strategyLogic1);

        context1.execute();

        StrategyLogic2 strategyLogic2 = new StrategyLogic2();
        ContextV1 context2 = new ContextV1(strategyLogic2);

        context2.execute();
    }

    /**
     * 전략 패턴 익명 내부 클래스 사용
     * */
    @Test
    void strategyV2() {
        ContextV1 context1 = new ContextV1(new Strategy() {

            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        });
        context1.execute();

        ContextV1 context2 = new ContextV1(new Strategy() {

            @Override
            public void call() {
                log.info("비즈니스 로직2 실행");
            }
        });
        context2.execute();
    }

    /**
     * 전략 패턴 익명 내부 클래스를 람다로 사용해서 실행.
     * 람다를 사용하려면 인터페이스에 메서드가 딱 한개만 있어야한다.
     * */
    @Test
    void strategyV3() {
        ContextV1 context1 = new ContextV1(() -> log.info("비즈니스 로직1 실행"));
        ContextV1 context2 = new ContextV1(() -> log.info("비즈니스 로직2 실행"));

        context1.execute();
        context2.execute();
    }
}
