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
        Class<?> classHello = Class.forName("com.example.advanced.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        Method methodCallA = classHello.getMethod("callA");
        Method methodCallB = classHello.getMethod("callB");

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
