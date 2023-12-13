package com.example.advanced.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0() {
        Hello target = new Hello();

        log.info("Start");
        String result1 = target.callA();
        log.info("result1={}", result1);

        log.info("Start");
        String result2 = target.callB();
        log.info("result2={}", result2);
    }

    @Test
    void reflection1() throws
            ClassNotFoundException,
            NoSuchMethodException,
            InvocationTargetException,
            IllegalAccessException {
        // 1. 패키지 명으로 클래스를 가져온다.
        Class<?> classHello = Class.forName("com.example.advanced.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();

        // 2. 위 1번 방법으로 가져온 클래스로부터 메서드를 이름을 통해 찾는다.
        Method methodCallA = classHello.getMethod("callA");
        Method methodCallB = classHello.getMethod("callB");

        // 3. 찾은 메서드의 invoke()를 실행하면 해당 메서드를 실행하는데 이 때 어떤 객체의 메서드를 실행할지 파라미터로 넘겨준다.
        Object result1 = methodCallA.invoke(target);
        Object result2 = methodCallB.invoke(target);

        log.info("result1={}", result1);
        log.info("result2={}", result2);
    }

    @Test
    void reflection2() throws Exception {
        Class<?> classHello = Class.forName("com.example.advanced.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        Method methodCallA = classHello.getMethod("callA");
        Method methodCallB = classHello.getMethod("callB");

        dynamicCall(methodCallA, target);
        dynamicCall(methodCallB, target);
    }

    private void dynamicCall(Method method, Object target) throws InvocationTargetException, IllegalAccessException {
        log.info("Start");
        Object result = method.invoke(target);
        log.info("result = {}", result);
    }

    static class Hello {
        public String callA() {
            log.info("callA");
            return "A";
        }

        public String callB() {
            log.info("callB");
            return "B";
        }
    }
}
