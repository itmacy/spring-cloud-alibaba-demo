# OpenFeign
代替restTemplate的请求，在Feign的基础上进行拓展，使用springMVC的注解来实现Feign的功能

## 远程调用
1. 引入依赖
```
    <!--   openfeign依赖     -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>
```
2. 添加调用rest接口的服务
```
/**
 * name:声明需要调用rest接口的服务名称
 * path:声明需要调用rest接口controller注解的requestMapping
 */
@FeignClient(name = "stock-service", path = "/stock")
public interface StockFeignService {

    // 声明需要调用rest接口的方法
    @GetMapping("/deduct")
    String deduct();
}
```
3. 在入口类添加注解: @EnableFeignClients
4. 调用: String msg = stockFeignService.deduct();

## 配置openfeign日志
在配置feign日志之前，首先要设置springboot的日志级别，在yml文件配置
```
# springboot默认的日志级别是info,feign的debug日志级别就不会显示，因此需要修改日志级别
logging:
  level: # debug # 配在这里是全局配置
    com.itmacy.dev.feign: debug # 局部配置，针对某个包
```
### 全局配置
针对所有的远程服务
1. 定义配置类，并在类上加上注解@Configuration
2. 定义日志级别的方法，并在该方法上加上注解@Bean
```
@Configuration
public class OpenFeignLogConfig {

    @Bean
    public Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
}
```
### 局部配置
只对指定的远程服务有效
#### 方式一
1. 定义配置类
2. 定义日志级别的方法，并在该方法上加上注解@Bean
```
public class OpenFeignLogConfig {

    @Bean
    public Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
}
```
3. 在远程服务的注解上加上configuration参数
```
// configuration ：配置局部日志，OpenFeignLogConfig类上不能加注解@Configuration
@FeignClient(name = "stock-service1", path = "/stock", configuration = OpenFeignLogConfig.class)
```
#### 方式二
使用yml配置
```
# feign日志的局部配置
feign:
  client:
    config: 
      stock-service:  # 针对该服务的设置
        loggerLevel: FULL # 日志级别：NONE、BASIC、HEADERS、FULL
```
## 自定义openfeign拦截器
通过自定义拦截器，可以在发送远程调用前，增加一些其他操作，例如header头部信息
1. 自定义拦截器类，并实现RequestInterceptor接口
```
public class OpenFeignCustomInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 添加请求头
        requestTemplate.header("tokenId", "xxx");
    }
}
```
2. 创建配置类，并把自定义的拦截器注入到spring中
```
@Configuration
public class OpenFeingInterceptorConfig {

    @Bean
    public OpenFeignCustomInterceptor openFeignCustomInterceptor(){
        return new OpenFeignCustomInterceptor();
    }
}
```