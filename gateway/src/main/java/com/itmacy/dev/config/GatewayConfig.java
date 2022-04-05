package com.itmacy.dev.config;


import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义流控异常
 * @author: itmacy
 * @date: 2022/4/4
 */
@Configuration
public class GatewayConfig {
    @PostConstruct
    public void init(){

        BlockRequestHandler blockRequestHandler = new BlockRequestHandler() {
            public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {
                System.out.println("=============" + throwable);
                // 自定义异常
                Map<String,String> map = new HashMap<>();
                map.put("code", HttpStatus.TOO_MANY_REQUESTS.toString());
                map.put("message", "限流了");

                return ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(map));
            }
        };
        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
    }
}
