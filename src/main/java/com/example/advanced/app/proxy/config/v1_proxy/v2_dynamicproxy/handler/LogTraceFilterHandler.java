package com.example.advanced.app.proxy.config.v1_proxy.v2_dynamicproxy.handler;

import com.example.advanced.trace.TraceStatus;
import com.example.advanced.trace.logtrace.LogTrace;
import org.springframework.util.PatternMatchUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * LogTraceBasicHandler 의 문제는 로그가 남으면 안되는 /v1/no-log 로 호출해도 로그가 남는다.
 * 왜냐하면 이 동적 프록시가 모든 메서드에 적용되기 때문에. 그것을 해결하는 Handler.
 * */
public class LogTraceFilterHandler implements InvocationHandler {

    private final Object target;
    private final LogTrace logTrace;
    private final String[] patterns;

    public LogTraceFilterHandler(Object target, LogTrace logTrace, String[] patterns) {
        this.target = target;
        this.logTrace = logTrace;
        this.patterns = patterns;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if (!PatternMatchUtils.simpleMatch(patterns, methodName)) {
            return method.invoke(target, args);
        }

        TraceStatus status = null;

        try {
            // Ex) "OrderController.request()"
            String message = method.getDeclaringClass().getSimpleName() + "." + method.getName() + "()";

            status = logTrace.begin(message);

            Object result = method.invoke(target, args);

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
