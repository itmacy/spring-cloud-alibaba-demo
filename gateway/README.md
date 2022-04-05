# gateway
spring cloud 实现的网关
## 入门
1. 引入依赖
```
    <!-- gateway依赖spring cloud开发 -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-gateway</artifactId>
    </dependency>
```
2. 配置yml文件
```
spring:
  cloud:
    gateway: # gateway配置
      routes:
        - id: order_route # 路由的唯一标识
          uri: lb://stock-service # 需要转发到的地址,lb:使用nacos中本地负载均衡策略，stock-service:需要转发的服务名
          predicates: # 断言规则，用于路由规则的匹配
            - Path=/stock-service/**
            # 例如由http://localhost:9008/stock-service/stock/deduct 路由到👇
            # http://localhost:9100/stock-service/stock/deduct
          filters:
            - StripPrefix=1 # 转发之前去除第一层路径
            # http://localhost:9100/stock/deduct
```
3.启动stock-nacos服务
4.访问：http://localhost:9008/stock-service/stock/deduct

## Reactor netty日志访问
### 打包后设置
运行：java -jar xxx.jar ­Dreactor.netty.http.server.accessLogEnabled=true
### idea设置
Edit Configuration,在VM options添加­Dreactor.netty.http.server.accessLogEnabled=true

## 跨域配置
### yml文件配置
```
spring:
  cloud:
    gateway: 
      # 跨域配置
      globalcors:
        cors-configurations:
          '[/**]': # 允许跨域的资源
            allowedOrigins: "*" # 跨域的来源
            allowedMethods:
              - GET
              - POST
              - DELETE
              - PUT
              - OPTION
```
### Java配置
```
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
```

## 自定义全局过滤器
```
@Component
public class LogFilter implements GlobalFilter {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("全局过滤器：" + exchange.getRequest().getURI());
        return chain.filter(exchange);
    }
}
```

## gateway整合sentinel
1. 引入依赖
```
    <!-- sentinel整合gateway-->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-alibaba-sentinel-gateway</artifactId>
    </dependency>
    <!-- sentinel核心依赖-->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
    </dependency>
```
2. yml配置
```
spring:
  cloud:
    sentinel:
      transport:
        dashboard: 127.0.0.1:9090 # 配置sentinel dashboard地址
```
3. 控制台流控设置/代码流控设置

## 自定义异常
- yml方式
```
spring:
  cloud:
    sentinel:
      # 自定义异常
      scg:
        fallback:
          mode: response
          response-body: '{"code": 403, "message": "限流了"}'
```
- 代码方式
```
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
```
