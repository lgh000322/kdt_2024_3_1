package shop.shopBE.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import shop.shopBE.global.logtrace.aspect.LogTraceAspect;
import shop.shopBE.global.logtrace.material.LogTrace;

@Configuration
@EnableAspectJAutoProxy
public class AopConfig {

//    @Bean
//    public LogTraceAspect logTraceAspect(LogTrace logTrace) {
//        return new LogTraceAspect(logTrace);
//    }
}
