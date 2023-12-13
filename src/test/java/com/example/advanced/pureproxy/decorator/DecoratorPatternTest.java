package com.example.advanced.pureproxy.decorator;

import com.example.advanced.pureproxy.decorator.code.DecoratorPatternClient;
import com.example.advanced.pureproxy.decorator.code.MessageDecorator;
import com.example.advanced.pureproxy.decorator.code.RealComponent;
import com.example.advanced.pureproxy.decorator.code.TimeDecorator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class DecoratorPatternTest {

    @Test
    void noDecorator() {
        RealComponent realComponent = new RealComponent();
        DecoratorPatternClient client = new DecoratorPatternClient(realComponent);

        client.execute();
    }

    @Test
    void decorator1() {
        RealComponent realComponent = new RealComponent();
        MessageDecorator messageDecorator = new MessageDecorator(realComponent);

        // 클라이언트는 프록시(데코레이터)가 주입되는지 실제 서비스가 주입되는지 모른다. 클라이언트 내부는 단지 인터페이스를 주입받기 때문에
        // 다시 말해, 프록시(데코레이터) 패턴은 클라이언트와 서비스의 어떠한 코드도 수정되지 않고 부가기능 또는 접근제어 기술을 적용할 수 있다.
        DecoratorPatternClient client = new DecoratorPatternClient(messageDecorator);

        client.execute();
    }

    @Test
    void decorator2() {
        RealComponent realComponent = new RealComponent();
        MessageDecorator messageDecorator = new MessageDecorator(realComponent);
        TimeDecorator timeDecorator = new TimeDecorator(messageDecorator);

        // 프록시(데코레이터) 패턴은 이렇게 체인으로 여러개의 프록시(데코레이터)를 엮을 수 있다.
        DecoratorPatternClient client = new DecoratorPatternClient(timeDecorator);

        client.execute();
    }
}
