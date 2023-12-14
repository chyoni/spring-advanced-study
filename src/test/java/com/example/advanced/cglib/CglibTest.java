package com.example.advanced.cglib;

import com.example.advanced.cglib.code.TimeMethodInterceptor;
import com.example.advanced.common.service.ConcreteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

/**
 * CGLIB는 JDK Dynamic Proxy가 해결하지 못하는 구체 클래스를 동적 프록시로 만들 수 있는 녀석이다.
 * 원래는 외부 라이브러리인데 스프링이 이 라이브러리를 사용해서 아무런 라이브러리를 받지 않아도 사용할 수 있다.
 * */
@Slf4j
public class CglibTest {

    @Test
    void cglib() {
        // 실제 객체가 될 구체 클래스
        ConcreteService target = new ConcreteService();

        // CGLIB를 생성하기 위한 Enhancer
        Enhancer enhancer = new Enhancer();

        // 구체 클래스를 프록시로 만들려면 필요했던 상속을 여기서도 마찬가지로 해줘야 한다.
        enhancer.setSuperclass(ConcreteService.class);

        // 구체 클래스의 기능에 플러스로 추가 기능을 제공하는 녀석을 JDK Dynamic Proxy는 InvocationHandler, CGLIB는 MethodInterceptor로 정의한다.
        enhancer.setCallback(new TimeMethodInterceptor(target));

        // 동적 프록시 생성
        ConcreteService proxy = (ConcreteService) enhancer.create();

        log.info("targetClass = {}", target.getClass());
        log.info("proxyClass = {}", proxy.getClass());

        // 동적 프록시가 가진 메서드 호출
        proxy.call();
    }
}
