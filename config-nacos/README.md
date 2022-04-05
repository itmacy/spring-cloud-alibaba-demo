# nacos配置中心
1. 引入依赖
```
    <!-- nacos config 依赖 -->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
    </dependency>
```
2. 在resources资源文件下，创建名为bootstrap.yml的文件
3. 在bootstrap.yml的文件进行nacos配置
```
spring:
  application:
    name: config-nacos
  cloud:
    nacos:
      server-addr: 127.0.0.1:8848
```
4. 在nacos控制台的配置列表添加dataId为服务名，文件格式为properties的配置
```
username= macy
password= 123
desc= hello config
```
5. 读取配置信息
```
 ConfigurableApplicationContext context = SpringApplication.run(ConfigNacosApplication.class, args);
    // 读取在nacos控制台设置的配置列表，dataId为服务名称，读取properties文件内容
    while (true){
        String username = context.getEnvironment().getProperty("username");
        String password = context.getEnvironment().getProperty("password");
        String desc = context.getEnvironment().getProperty("desc");
        System.out.println(username + ":" + password + ":" + desc);
        Thread.sleep(1000* 3);
    }
```
## 文件扩展名的配置
nacos客户端端默认读取的是properties文件扩展名，如果要修改文件拓展名为yaml，则需要进行配置
1. 修改读取的文件扩展名
```
spring:
  application:
    name: config-nacos
  cloud:
    nacos:
      server-addr: 127.0.0.1:8848
        # 注册中心配置
      config:
        file-extension: yaml # nacso控制台dataId默认支持的是properties文件
```

2.读取nacos控制台配置文件的优先级
对于不同dataId的文件，如果属性一致的情况下，读取的优先级如下
- profile：dataId为 ${spring.application.name}-激活的环境.${file-extension}，例如dataId：config-nacos-dev.yaml
- 默认配置：dataId为 ${spring.application.name},例如dataId：config-nacos
- shared-configs，下标越大，优先级越高，例如
```
spring:
  application:
    name: config-nacos
  cloud:
    nacos:
      server-addr: 127.0.0.1:8848
        # 注册中心配置
      config:
        # nacso控制台dataId默认支持的是properties文件
        file-extension: yaml  # 修改读取的文件后缀为yaml
        shared-configs: # 加载自定义的拓展文件，一般用于读取公共的配置文件，后读取的优先级更高
          - data-id: com.itmacy.dev.common1.yaml # 自定义的dataId
            refresh: true # 动态感知刷新
#            group: DEFAULT_GROUP # 默认分组
          - data-id: com.itmacy.dev.common2.yaml # 自定义的dataId
            refresh: true # 动态感知刷新
#            group: DEFAULT_GROUP # 默认分组
```

## 动态感知参数变化
为了让@Value注解动态感知参数的变化，可以在类上加上注解@RefreshScope
```
@RestController
@RequestMapping("config")
@RefreshScope
public class ConfigController {

    @Value("${username}")
    String username;
    @Value("${password}")
    String password;
    @Value("${desc}")
    String desc;

    @GetMapping("test")
    public String test(){
        return username + ":" + password + ":" + desc;
    }
}
```
## 配置中心持久化
1. 在conf/application.propertise文件修改db连接，去除相关注释
2. 创建数据库
3. 运行conf/nacos-mysql.sql文件生成数据库表
4. 当在nacos配置中心进行添加data-id时，就会保存在数据库中