package com.example.advanced;

// import com.example.advanced.app.proxy.config.v1_proxy.v2_dynamicproxy.DynamicProxyBasicConfig;
import com.example.advanced.app.proxy.config.v3_proxyfactory.ProxyFactoryConfigV1;
import com.example.advanced.app.proxy.config.v3_proxyfactory.ProxyFactoryConfigV2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

// @Import({AppV1Config.class, AppV2Config.class})
// @Import(InterfaceProxyConfig.class)
// @Import(ConcreteProxyConfig.class)
// @Import({DynamicProxyFilterConfig.class, LogTraceConfig.class})
@Import({ProxyFactoryConfigV1.class, ProxyFactoryConfigV2.class, LogTraceConfig.class})
@SpringBootApplication(scanBasePackages = "com.example.advanced.app.proxy")
public class AdvancedApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdvancedApplication.class, args);
    }

}
