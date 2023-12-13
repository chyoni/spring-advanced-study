package com.example.advanced.pureproxy.concreteproxy;

import com.example.advanced.pureproxy.concreteproxy.code.ConcreteClient;
import com.example.advanced.pureproxy.concreteproxy.code.ConcreteLogic;
import com.example.advanced.pureproxy.concreteproxy.code.TimeProxy;
import org.junit.jupiter.api.Test;

public class ConcreteProxyTest {

    @Test
    void noProxy() {
        ConcreteLogic concreteLogic = new ConcreteLogic();
        ConcreteClient client = new ConcreteClient(concreteLogic);

        client.execute();
    }

    @Test
    void addProxy() {
        ConcreteLogic concreteLogic = new ConcreteLogic();
        TimeProxy timeProxy = new TimeProxy(concreteLogic);

        // ConcreteClient는 ConcreteLogic을 주입받지만 실제로 주입받는건 TimeProxy이다. 이게 가능한 이유는 TimeProxy는 ConcreteLogic을 상속받기 때문.
        // 부모 타입에 자식은 들어갈 수 있다. (그 반대는 안돼요)
        ConcreteClient client = new ConcreteClient(timeProxy);

        client.execute();
    }
}
