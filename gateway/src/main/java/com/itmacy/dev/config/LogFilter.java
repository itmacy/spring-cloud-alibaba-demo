package com.itmacy.dev.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 自定义全局过滤器
 * @author: itmacy
 * @date: 2022/4/4
 */
@Component
public class LogFilter implements GlobalFilter {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("全局过滤器：" + exchange.getRequest().getURI());
        return chain.filter(exchange);
    }
}
