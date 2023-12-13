package com.example.advanced.jdkdynamic;

import com.example.advanced.jdkdynamic.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

/**
 * ⭐️ JDK 동적 프록시는 인터페이스가 필수이다.
 * */
@Slf4j
public class JdkDynamicProxyTest {

    @Test
    void dynamicA() {
        // AInterface 로 프록시를 만들고자 함.
        AInterface target = new AImpl();

        // JDK 를 이용해서 동적 프록시를 만드려면 InvocationHandler 를 상속받은 클래스가 필요하다.
        // 그 클래스는 target(프록시를 만들려는 실제 객체)을 생성자로부터 받는다.
        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        // 동적 프록시를 생성 Reflection Proxy (JDK)
        AInterface proxy = (AInterface) Proxy
                .newProxyInstance(AInterface.class.getClassLoader(), new Class[]{AInterface.class}, handler);

        // 동적 프록시(AInterface 를 구현한 클래스의)가 가진 메서드 call() 호출.
        // 이렇게 프록시가 자기가 가진 메서드를 호출하면 무조건 newProxyInstance 에서 전달한 handler 의 invoke 를 실행
        // invoke 는 (Object proxy, Method method, Object[] args) 이러한 파라미터를 가지는데 저 가운데 method 가 이 경우엔 call() 이 된다.
        proxy.call();
        log.info("targetClass = {}", target.getClass());
        log.info("proxyClass = {}", proxy.getClass());
    }

    @Test
    void dynamicB() {
        BInterface target = new BImpl();

        // 다른 구현 클래스의 프록시를 만들때도 같은 TimeInvocationHandler 를 사용할 수 있음 => 동적 프록시
        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        BInterface proxy = (BInterface) Proxy
                .newProxyInstance(BInterface.class.getClassLoader(), new Class[]{BInterface.class}, handler);

        proxy.call();

        log.info("targetClass = {}", target.getClass());
        log.info("proxyClass = {}", proxy.getClass());
    }
}
