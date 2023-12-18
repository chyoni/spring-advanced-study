package com.example.advanced.app.proxy.config.v6_aop;

import com.example.advanced.AppV1Config;
import com.example.advanced.AppV2Config;
import com.example.advanced.LogTraceConfig;
import com.example.advanced.app.proxy.config.v6_aop.aspect.LogTraceAspect;
import com.example.advanced.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppV1Config.class, AppV2Config.class, LogTraceConfig.class})
public class AopConfig {

    @Bean
    public LogTraceAspect logTraceAspect(LogTrace logTrace) {
        return new LogTraceAspect(logTrace);
    }
}
