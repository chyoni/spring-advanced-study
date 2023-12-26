package com.example.advanced.trace.callback;

/**
 * 인터페이스에 구현할 메서드가 딱 하나면 람다를 사용할 수 있다.
 * */
public interface TraceCallback<T> {
    T call();
}
