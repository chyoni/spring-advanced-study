package com.example.advanced.pureproxy.proxy;

import com.example.advanced.pureproxy.proxy.code.CacheProxy;
import com.example.advanced.pureproxy.proxy.code.ProxyPatternClient;
import com.example.advanced.pureproxy.proxy.code.RealSubject;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {

    @Test
    void noProxyTest() {
        RealSubject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);

        client.execute();
        client.execute();
        client.execute();
    }

    @Test
    void cacheProxyTest() {
        RealSubject realSubject = new RealSubject();
        CacheProxy cacheProxy = new CacheProxy(realSubject);

        // 클라이언트는 프록시(데코레이터)가 주입되는지 실제 서비스가 주입되는지 모른다. 클라이언트 내부는 단지 인터페이스를 주입받기 때문에
        // 다시 말해, 프록시(데코레이터) 패턴은 클라이언트와 서비스의 어떠한 코드도 수정되지 않고 부가기능 또는 접근제어 기술을 적용할 수 있다.
        ProxyPatternClient client = new ProxyPatternClient(cacheProxy);

        client.execute();
        client.execute();
        client.execute();
    }
}
