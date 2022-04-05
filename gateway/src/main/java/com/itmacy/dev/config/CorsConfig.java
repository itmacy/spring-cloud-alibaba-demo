package com.itmacy.dev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * 跨域配置
 * @author: itmacy
 * @date: 2022/4/4
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*"); // 允许的请求方式
        config.addAllowedOrigin("*"); // 允许的来源
        config.addAllowedHeader("*"); // 允许的头部

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config); // 允许访问的资源

        return new CorsWebFilter(source);
     }

}
