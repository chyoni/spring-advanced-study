package com.example.advanced.advisor;

import com.example.advanced.common.advice.TimeAdvice;
import com.example.advanced.common.service.ServiceImpl;
import com.example.advanced.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import java.lang.reflect.Method;

/**
 * Advisor는 Advice와 Pointcut을 가지고 있는 아이다.
 * ProxyFactory는 프록시를 만들어 낼 때 이 Advisor를 만들어내는것과 같은데 Advice는 추가 기능 즉, 프록시가 제공하는 추가기능에 대한 로직이 담겨 있는 아이다.
 * 그리고 Pointcut은 필터링 로직이다. 즉, 어떤 요청에는 이 Advice의 로직을 적용하고 어떤 요청에는 이 Advice의 로직을 적용하지 않게 하는 그런 필터링 로직.
 * 그리고 Advisor가 이 두개를 가지고 있는 녀석이라고 보면 된다. ProxyFactory는 반드시 Advisor를 만들게 되어 있다.
 *
 * 쉽게 설명하면 Pointcut(어디에) Advice(조언 = 추가 기능)를 할 것인가?
 * 그 '어디에'에 해당하는 Pointcut과 '추가 기능' 에 해당하는 Advice를 가진 Advisor(조언자)
 * */
@Slf4j
public class AdvisorTest {

    @Test
    void advisorTest1() {
        // 실제 객체가 될 서비스 객체
        ServiceInterface target = new ServiceImpl();

        // ProxyFactory 객체를 생성
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // DefaultPointcutAdvisor 객체를 생성. 이 Advisor가 ProxyFactory가 만들어내야 하는 그 Advisor다.
        // 여기서 Pointcut.TRUE 는 모든 요청에 대해 내가 지금 전달한 TimeAdvice 로직을 적용하겠다는 의미다.
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());

        // ProxyFactory에 Advisor를 추가한다.
        proxyFactory.addAdvisor(advisor);

        // ProxyFactory로부터 프록시를 꺼낸다.
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    @Test
    @DisplayName("직접 만든 포인트컷")
    void advisorTest2() {
        // 실제 객체 서비스
        ServiceInterface target = new ServiceImpl();

        // ProxyFactory 객체 생성 후 실제 객체를 전달
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // ProxyFactory가 만들 Advisor
        // Pointcut을 직접 만들어서 넣었고, Advice도 만들어서 넣었음.
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new MyPointcut(), new TimeAdvice());

        // ProxyFactory에 advisor 추가
        proxyFactory.addAdvisor(advisor);

        // ProxyFactory 로부터 proxy 를 꺼내온다.
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        // Pointcut에 의하여 Advice 적용된다.
        proxy.save();
        // Pointcut에 의하여 Advice 적용되지 않는다.
        proxy.find();
    }

    /**
     * 직접 만들일은 없음, 스프링이 만들어주는 Pointcut을 사용하면 되지만 한번 만들어보자.
     * 클래스와 메서드 둘 다 'true' 를 리턴해야만 Pointcut에 적합한 요청이라고 판단하여 Advice를 적용한다.
     * */
    static class MyPointcut implements Pointcut {
        /**
         * 클래스를 기준으로 필터링
         * ClassFilter.TRUE 를 반환하면 모든 클래스에 대해 Advice 적용을 허용
         * */
        @Override
        public ClassFilter getClassFilter() {
            return ClassFilter.TRUE;
        }

        /**
         * 메서드를 기준으로 필터링
         * MethodMatcher를 구현해야 한다.
         * */
        @Override
        public MethodMatcher getMethodMatcher() {
            return new MyMethodMatcher();
        }
    }

    static class MyMethodMatcher implements MethodMatcher {

        private String matchName = "save";

        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            boolean result = method.getName().equals(matchName);

            log.info("포인트컷 호출 method = {} targetClass= {}", method.getName(), targetClass);
            log.info("포인트컷 결과 result = {}", result);

            return result;
        }

        @Override
        public boolean isRuntime() {
            return false;
        }

        @Override
        public boolean matches(Method method, Class<?> targetClass, Object... args) {
            return false;
        }
    }
}
