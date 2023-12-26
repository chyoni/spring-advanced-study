package com.example.advanced.trace.template;

import com.example.advanced.trace.TraceStatus;
import com.example.advanced.trace.logtrace.LogTrace;

/**
 * 템플릿 메서드 패턴 사용을 위한 추상 클래스.
 * 이 클래스는 공통되는 부분을 추상 클래스에서 구현하고 공통 부분이 아닌 부분을 이 클래스를 상속받는 자식 클래스에서 구현하여
 * 그때 그때 달라지는 공통 부분이 아닌 로직을 처리한다.
 * */
public abstract class AbstractTemplate<T> {

    private final LogTrace logTrace;

    public AbstractTemplate(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    public T execute(String message) {
        TraceStatus status = null;

        try {
            status = logTrace.begin(message);

            T result = call();

            logTrace.end(status);

            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }

    protected abstract T call();
}
