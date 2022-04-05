package com.itmacy.dev.config;

import com.itmacy.dev.interceptor.OpenFeignCustomInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * openfeign拦截器配置
 * author: itmacy
 * date: 2022/3/28
 */
@Configuration
public class OpenFeingInterceptorConfig {

    @Bean
    public OpenFeignCustomInterceptor openFeignCustomInterceptor(){
        return new OpenFeignCustomInterceptor();
    }
}
