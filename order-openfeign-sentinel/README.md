# sentinel
流量控制，面向分布式服务架构的高可用防护组件
## 控制台使用
1. 下载对应版本的jar包（下载的版本与spring cloud alibaba版本对应）：https://github.com/alibaba/Sentinel/releases
2. 运行：java -jar xxx
3. 访问：localhost:8080
4. 用户名，密码都是：sentinel

修改端口号和用户名密码，使用bat文件
```
java -Dserver.port=9090 -Dsentinel.dashboard.auth.username=sentinel -Dsentinel.dashboard.auth.password=sentinel -jar E:/macy/environment/sentinel/sentinel-dashboard-1.8.1.jar
pause
```

## 客户端
1. 引入sentinel启动器
```
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
        </dependency>
```
2. 配置dashboard
```
spring:
  application:
    name: order-sentienl
  cloud: # 配置sentinel控制台地址
    sentinel:
      transport:
        dashboard: 127.0.0.1:9090
```
3. 多次访问服务的接口，就能在控制台看到页面了
4. 流控方式
   1. 在sentinel dashboard页面直接设置资源进行流控
   2. 编码方式进行流控

## sentinel统一异常处理
为了避免在每个接口上添加@SentinelResource注解，可以使用统一异常进行处理
1. 创建统一异常处理类，实现BlockExceptionHandler接口
```
@Component
public class SentinelExceptionHandler implements BlockExceptionHandler {}
```

## sentinel服务降级
当服务提供方出现异常时，在服务的消费为了能够继续完成任务，因此调用降级的方式
1. 自定义一个降级服务实现feign服务
```
@Service
public class StockFeignServiceFallback implements StockFeignService{
    @Override
    public String deduct() {
        return "降级服务";
    }
}
```
2. 在feign服务配置fallback
```
@FeignClient(name = "stock-service", path = "/stock", fallback = StockFeignServiceFallback.class)
public interface StockFeignService {

    @GetMapping("/deduct")
    String deduct();
}
```
3. 配置feign对sentinel的支持
```
feign:
  sentinel:
    enabled: true # 添加feign对sentinel的支持
```

## sentinel整合nacos持久化
1. 引入依赖
```
        <dependency>
            <groupId>com.alibaba.csp</groupId>
            <artifactId>sentinel-datasource-nacos</artifactId>
        </dependency>
```
2. 在yml文件配置
```
spring:
  application:
    name: order-feign-sentinel # 应用名称，nacos会把它作为服务名称
  cloud:
    nacos:
      server-addr: 127.0.0.1:8848
      discovery:
        username: nacos
        password: nacos
        namespace: public # 命名空间
    sentinel:
      transport:
        dashboard: 127.0.0.1:9090
      datasource:   # 配置sentinel持久化
        flow-rule: # 可以自定义，
          nacos: # 从nacos拉取数据到sentinel
            server-addr: 127.0.0.1:8848
            username: nacos
            password: nacos
            data-id: order-sentinel-flow-rule # 在控制台配置的dataId
            data-type: json  # 默认json格式，在控制台配置时选择JSON
            rule-type: flow  # 规则类型：流控
```
3. 在nacos控制台的配置中心进行dataId的配置,例如
```
 [
    {
    "resource": "/order/create",
    "controlBehavior": 0,
    "count": 2,
    "grade": 1,
    "limitApp": "default",
    "strategy": 0
    }
]
```
4. 多次点击接口：localhost:9005/order/create，可以看到返回的流控信息

