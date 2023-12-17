/*
package com.example.advanced.app.proxy.config.v4_postprocessor;

import com.example.advanced.LogTraceConfig;
import com.example.advanced.app.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import com.example.advanced.app.proxy.config.v4_postprocessor.postprocessor.PackageLogTracePostProcessor;
import com.example.advanced.AppV1Config;
import com.example.advanced.AppV2Config;
import com.example.advanced.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

*/
/**
 * Bean PostProcessor(빈후처리기)를 사용하는 이유는 컴포넌트 스캔 대상은 스프링이 자동으로 해당 컴포넌트를 빈으로 등록을 해버리기 때문에 우리의 프록시를 적용할 수가 없다.
 * 그래서 컴포넌트 스캔 대상이 되는 컴포넌트들을 프록시로 만들기 위해 스프링이 스프링 컨테이너에 최종적으로 빈으로 등록하기 바로 전 단계에서 우리의 프록시로 변환을 해주는 것.
 * 그리고 이것을 사용하면 기존에 컴포넌트 스캔 대상이 아닌 것들을 우리가 직접 빈으로 등록하면서 프록시를 입혔는데 그 번거로운 과정도 한번에 해결이 된다.
 * 그래서 프록시로 스프링 컨테이너에 올릴 우리의 컴포넌트들을 빈으로만 등록해놓는 간단한 Configuration 을 만든 후 이 빈 후처리기를 통해서 프록시로 입혀 최종적으로 스프링 컨테이너에 올리는 것.
 *
 * 그래서 AppV1Config, AppV2Config 파일을 보면 프록시를 적용하지 않은 V1, V2 컨트롤러, 서비스, 레포지토리만을 빈으로 등록하고 이 빈 후처리기에서
 * 내가 프록시로 등록되길 원하는 대상만을 패키지 명으로 검사해서 프록시를 입혀 스프링 컨테이너로 등록함.
 * V3 같은 경우 컴포넌트 스캔 대상이므로 따로 빈으로 등록할 필요가 없기 때문에 AppV3Config 같은 파일은 없다.
 * */

/*
@Slf4j
@Configuration
@Import({AppV1Config.class, AppV2Config.class, LogTraceConfig.class})
public class BeanPostProcessorConfig {

    @Bean
    public PackageLogTracePostProcessor logTracePostProcessor(LogTrace logTrace) {
        return new PackageLogTracePostProcessor("com.example.advanced.app.proxy", getAdvisor(logTrace));
    }

    private Advisor getAdvisor(LogTrace logTrace) {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
*/
