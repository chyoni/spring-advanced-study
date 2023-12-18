package com.example.advanced.app.proxy.config.v6_aop.aspect;

import com.example.advanced.trace.TraceStatus;
import com.example.advanced.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;


/**
 * {@code @Aspect} 가 붙어있는 녀석이 빈으로 등록되어 있으면 자동 프록시 생성기인 AnnotationAwareAspectJAutoProxyCreator 이 녀석이
 * {@code @Aspect} 가 붙어있는 이 녀석을 Advisor 로 만들어 준다. 이 때 Pointcut 은 @Around 가 달려있는 녀석이 포인트컷이 되고
 * 그 안 로직이 Advice 가 된다. 그리고 그 하나가 Advisor 가 된다. 만약 이 @Aspect 가 붙은 녀석이 @Around 가 여러개 있으면 Advisor 도 여러개가 되는 것.
 * 왜냐하면 @Around = 포인트컷, @Around 내부 로직 = 어드바이스, 포인트컷 + 어드바이스 = 어드바이저 이기 때문에 이게 여러개 있으면 어드바이저도 여러개가 생기는 것.
 * */
@Slf4j
@Aspect
public class LogTraceAspect {

    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    @Around("execution(* com.example.advanced.app.proxy.version..*(..))") // Pointcut
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        // Advice 로직
        TraceStatus status = null;

        try {
            // Ex) "OrderController.request()"
            String message = joinPoint.getSignature().toShortString();

            status = logTrace.begin(message);

            Object result = joinPoint.proceed();

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
