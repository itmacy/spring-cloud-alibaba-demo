# Ribbon
nacos内置了ribbon，默认的负载均衡策略是轮询
## 饥饿记载ribbon
```
# ribbon配置
ribbon:
  eager-load:
    enabled: true # 设置为饥饿加载，默认情况下，第一次调用服务时才会动态加载ribbon策略，使用饥饿加载，可以在项目启动时就立即加载
    clients: stock-service # 饥饿加载的服务，多个服务时用都好隔开
```


## 修改默认的负载均衡策略
### 使用配置类的方式
1. 创建一个配置类，该类必须不在入口类的扫描下
```
@Configuration
public class RibbonConfig {

    /**
     * 配置负载均衡策略：随机策略
     * @return
     */
    @Bean
    public IRule iRule(){
        return new RandomRule();
    }
}
```
2. 在入口类的上面添加注解
```
@RibbonClients({ // 配置ribbon客户端
        @RibbonClient(name = "stock-service", configuration = RibbonConfig.class)
})
```
### 使用文件配置的方式
```
stock-service:
  ribbon:
    NFLoadBalancerRuleClassName: com.alibaba.cloud.nacos.ribbon.NacosRule # 基于随机&权重
```
