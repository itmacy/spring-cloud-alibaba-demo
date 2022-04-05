package com.itmacy.dev.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * author: itmacy
 * date: 2022/3/29
 */
@Configuration
public class RuleConfiguration {
    @Bean
    public SentinelResourceAspect sentinelResourceAspect () {
        return new SentinelResourceAspect();
    }
}
