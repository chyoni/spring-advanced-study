package com.example.advanced.app.proxy.config.v5_autoproxy;

import com.example.advanced.AppV1Config;
import com.example.advanced.AppV2Config;
import com.example.advanced.LogTraceConfig;
import com.example.advanced.app.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import com.example.advanced.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 스프링이 제공하는 빈후처리기 사용.
 *
 * 자, 바로 전에는 v4_postprocessor 에서 빈후처리기를 사용해서 컴포넌트 스캔 대상이 되는 컴포넌트들까지 프록시를 입혀봤다.
 * 이유는 컴포넌트 스캔 대상이 되는 녀석들은 스프링이 자동으로 빈으로 등록해버리기 때문에 프록시를 입힐 수 없어 프록시를 입히기 위해 빈 후처리기를 통해 스프링 컨테이너에 최종적으로 등록하기 전 프록시를 입혀 등록하기 위해서.
 * 그런데, 스프링이 이런 기능을 해주는 빈후처리기까지도 제공해준다.
 * dependency 로 org.springframework.boot:spring-boot-starter-aop 를 등록하면 방금 위에서 말한 빈후처리기가 제공된다.
 * 이 빈후처리기의 이름은 AnnotationAwareAspectJAutoProxyCreator 이고 이 녀석은 어떻게 우리가 프록시로 입혀지길 원하는 녀석들에게 프록시를 입힐까?
 * 정답은 Pointcut 에 있다. Advisor 에는 무조건 Pointcut, Advice 가 있다고 했다.
 * Pointcut 은 Advice 가 적용될 대상을 설명하는 녀석인데 우리가 Advisor 를 스프링 빈으로 등록만 해놓으면 이 스프링이 제공하는 빈후처리기가 빈으로 등록된 모든 Advisor 를 찾아서
 * 그 Advisor 가 가지고 있는 Pointcut 을 가지고 와서 프록시가 적용될 대상들을 자동으로 찾아준다.
 * 합리적이다. 왜냐하면 Advice 를 적용할 대상을 Pointcut 으로 지정한 거니까 Pointcut 을 보고 프록시로 만들 녀석을 찾는다는 건.
 *
 * 예를 들어, 아래 Advisor 처럼 pointcut 에 적용될 메서드 명의 regex 를 정의하면 이 메서드명과 일치하는 메서드를 가지고 있는 녀석들을 프록시로 만든다는 것이다.
 * OrderControllerV1,V2,V3 모두 request()를 가지고 있지? 그럼 OrderControllerV1,V2,V3 모두 이 Advisor 를 가지는 프록시가 만들어진다.
 * OrderServiceV1,V2,V3 모두 orderItem()을 가지고 있지? 그럼 OrderServiceV1,V2,V3 모두 이 Advisor 를 가지는 프록시가 만들어진다.
 *
 * 그러니까 결론은, Advisor 만 빈으로 잘 등록해놓으면 그 이후에 프록시를 입힐 대상이 컴포넌트 스캔이든 수동 빈으로 등록된 것이든 간 우리는 걱정할 필요없이 프록시로 스프링 컨테이너에 등록되는 것을 보장받을 수 있다.
 * */
@Slf4j
@Configuration
@Import({AppV1Config.class, AppV2Config.class, LogTraceConfig.class})
public class AutoProxyConfig {

    //@Bean
    public Advisor getAdvisor1(LogTrace logTrace) {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    /**
     * 왜 getAdvisor1()를 사용하지 않냐면, 저 pointcut은 메서드 명이 regular expression 으로 'request*' 에 매칭되기만 하면 다 프록시로 등록하기 때문에
     * 내가 원하지 않는 녀석들까지 프록시로 입혀버린다. 난 그게 아니라 OrderControllerV1,V2,V3 / OrderServiceV1,V2,V3 / OrderRepositoryV1,V2,V3 얘네만 원하는 것이다.
     * 그래서 다른 Pointcut 을 사용한다. 이 Pointcut 을 사용하면 regex 로 좀 더 세밀화할 수 있다.
     * */
    //@Bean
    public Advisor getAdvisor2(LogTrace logTrace) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();

        // com.example.advanced.app.proxy.version 하위의 모든 파일(..)의 모든 메서드(*)의 모든 파라미터( (..) )를 가지는 녀석들에게 적용
        pointcut.setExpression("execution(* com.example.advanced.app.proxy.version..*(..))");

        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    /**
     * getAdvisor2()를 사용하지 않는 이유는 저건 원하지 않는 메서드 noLog() 도 Advice 가 적용된다. 왜냐하면 모든 메서드(*)에 적용했으니까.
     * 그것을 빼주는 getAdvisor3().
     *
     * 그리고 이 AspectJExpressionPointcut 이 실무에서도 가장 많이 사용되는 Pointcut 이다.
     * */
    @Bean
    public Advisor getAdvisor3(LogTrace logTrace) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();

        // com.example.advanced.app.proxy.version 하위의 모든 파일(..)의 모든 메서드(*)의 모든 파라미터( (..) )를 가지는 녀석들에게 적용 +
        // com.example.advanced.app.proxy.version 하위의 모든 파일(..)의 메서드(noLog)의 모든 파라미터( (..) )를 가지는 녀석은 적용X
        pointcut.setExpression("execution(* com.example.advanced.app.proxy.version..*(..)) &&" +
                " !execution(* com.example.advanced.app.proxy.version..noLog(..))");

        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
