package com.example.advanced.proxyfactory;

import com.example.advanced.common.advice.TimeAdvice;
import com.example.advanced.common.service.ConcreteService;
import com.example.advanced.common.service.ServiceImpl;
import com.example.advanced.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import static org.assertj.core.api.Assertions.*;

/**
 * JDK Dynamic Proxy와 CGLIB는 큰 단점이 있다. JDK 동적 프록시는 인터페이스로 반드시 프록시를 만들어야 하고
 * CGLIB는 구체 클래스를 가지고 프록시를 만든다. CGLIB는 MethodInterceptor JDK 동적 프록시는 InvocationHandler를 이용하는데
 * 그럼 그 때마다 저 둘을 섞어 사용하는 번거로움을 개발자가 가져가야할까? 아니다. 스프링이 도와준다. 스프링은 ProxyFactory를 통해
 * 인터페이스면 JDK Dynamic Proxy로, 구체 클래스면 CGLIB로 프록시를 만들어준다. 그 때 필요한 건 Advice라는 녀석이다.
 * */
@Slf4j
public class ProxyFactoryTest {

    @Test
    @DisplayName("인터페이스가 있으면 JDK 동적 프록시를 사용")
    void interfaceFactory() {
        // 프록시로 만들어 낼 실제 객체
        ServiceInterface target = new ServiceImpl();

        // ProxyFactory를 사용해서 동적 프록시를 만든다.
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // ProxyFactory를 이용하기 위해선 스프링에서 제공하는 Advice 인터페이스 구현한 클래스가 필요하다.
        // 그 Advice를 상속받는 Interceptor를 상속받는 MethodInterceptor를 구현한 클래스를 만들었다. (TimeAdvice)
        // addAdvice()로 Advice만 ProxyFactory에 추가하면 DefaultPointcutAdvisor(Pointcut.TRUE)가 생성된다.
        // advisor 를 간단하게 생성하는 편의성 메서드라고 보면 된다.
        proxyFactory.addAdvice(new TimeAdvice());

        // proxyFactory에서 proxy를 꺼내온다. 이 proxy는 인터페이스를 제공했으면 JDK Dynamic Proxy로 만들어지고
        // 구체 클래스를 제공했으면 CGLIB 프록시로 만들어진다.
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        log.info("targetClass = {}", target.getClass());
        log.info("proxyClass = {}", proxy.getClass());

        // 만든 프록시로 실제 객체(ServiceImpl)의 메서드를 실행
        proxy.save();

        // AopUtils.isAopProxy()는 스프링에서 제공해주는 기능인데 Proxy 인지를 알려준다.
        // ProxyFactory를 사용해서 만든 Proxy여야만 사용할 수 있다.
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
        assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
    }

    @Test
    @DisplayName("구체 클래스만 있으면 CGLIB 사용")
    void concreteFactory() {
        ConcreteService target = new ConcreteService();

        ProxyFactory proxyFactory = new ProxyFactory(target);

        proxyFactory.addAdvice(new TimeAdvice());

        ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();

        log.info("targetClass = {}", target.getClass());
        log.info("proxyClass = {}", proxy.getClass());

        proxy.call();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }

    @Test
    @DisplayName("ProxyTargetClass 옵션을 사용하면 인터페이스가 있어도 CGLIB 를 사용하고 클래스 기반 프록시 사용")
    void proxyTargetClass() {
        ServiceInterface target = new ServiceImpl();

        ProxyFactory proxyFactory = new ProxyFactory(target);

        // Proxy를 만드는데 Target의 클래스를 기반으로 (즉, 구체 클래스로) 만들것인지에 대한 옵션. 중요하다⭐️
        proxyFactory.setProxyTargetClass(true);

        proxyFactory.addAdvice(new TimeAdvice());

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        log.info("targetClass = {}", target.getClass());
        log.info("proxyClass = {}", proxy.getClass());

        proxy.save();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }
}
