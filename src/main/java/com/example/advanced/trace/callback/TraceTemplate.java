package com.example.advanced.trace.callback;

import com.example.advanced.trace.TraceStatus;
import com.example.advanced.trace.logtrace.LogTrace;

/**
 * 템플릿 콜백 패턴 사용.
 * 이 클래스는 공통 부분 로직을 이 템플릿에 할당해두고 변하는 부분을 파라미터(TraceCallback)로 받아 변하는 로직 실행을 위임하는 것.
 * 반환 타입 또한 제네릭으로 더 유연하게 받아주면서 누군가 이 템플릿을 호출할 때 전달하는 TraceCallBack 인터페이스의 구현된 call 메서드를 실행한다.
 * */
public class TraceTemplate {

    private final LogTrace logTrace;

    public TraceTemplate(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    public <T> T execute(String message, TraceCallback<T> callback) {
        TraceStatus status = null;

        try {
            status = logTrace.begin(message);

            T result = callback.call();

            logTrace.end(status);

            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
