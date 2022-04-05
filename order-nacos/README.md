# Nacos服务注册发现
一个集注册中心、配置中心、服务管理的平台
官网：https://nacos.io/zh-cn/docs/quick-start.html
### nacos服务端
1. 下载（下载的版本与spring cloud alibaba版本对应）：https://github.com/alibaba/nacos/releases
2. 解压，如果是单点部署，那么修改nacos/bin/startup.cmd文件的模式为standalone:set MODE="standalone"
3. 双击启动startup.cmd文件
4. 默认的用户名密码都是：nacos
5. 访问控制台

### nacos客户端
1. 引入依赖
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<!--  nacos服务注册发现      -->
 <dependency>
     <groupId>com.alibaba.cloud</groupId>
     <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
 </dependency>
```
2.yml文件配置
```
spring:
  cloud:
    nacos:
      server-addr: 127.0.0.1:8848
      discovery:
        username: nacos
        password: nacos
        namespace: public # 命名空间，默认的是public，以更细的相同特征的服务归类分组管理
        ephemeral: false  # 设置为永久实例，即使宕机也不从服务列表中删除
#        service: stock-service 默认取得是${spring.application.name}
#        group: DEFAULT_GROUP  以更细的相同特征的服务归类分组管理
#        weight: 1 结合权重的负载均衡策略，权重越高，分配的流量越大
#        metadata: version=1  可以结合元数据做服务的扩展
```
3. 启动项目
4. 在控制台可以看到注册的服务
