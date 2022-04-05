# skywalking
分布式系统的应用程序性能监控工具
## 服务器端
1. 下载：https://skywalking.apache.org/downloads/
2. 解压，运行/bin/startup.bat
3. 访问：http://localhost:8080（可以在weapp/weapp.yml文件修改端口：目前改为8868）

说明：
启动成功后会启动两个服务，一个是skywalking-oap-server，一个是skywalking-web-ui  ： 8080，在weapp/weapp.yml文件修改端口
skywalking-oap-server服务启动后会暴露11800 和 12800 两个端口，分别为收集监控数据的端口11800和接受前端请
求的端口12800，修改端口可以修改config/applicaiton.yml
## 客户端
### 部署方式
在Linux下编写shell脚本
```
#!/bin/sh
# SkyWalking Agent配置
export SW_AGENT_NAME=springboot‐skywalking‐demo #Agent名字,一般使用`spring.application.name`
export SW_AGENT_COLLECTOR_BACKEND_SERVICES=127.0.0.1:11800 #配置 Collector 地址。
export SW_AGENT_SPAN_LIMIT=2000 #配置链路的最大Span数量，默认为 300。
export JAVA_AGENT=‐javaagent:/usr/local/soft/apache‐skywalking‐apm‐bin‐es7/agent/skywalking‐agent.jar
java $JAVA_AGENT ‐jar springboot‐skywalking‐demo‐0.0.1‐SNAPSHOT.jar #jar启动
```

等同于
```
java ‐javaagent:/usr/local/soft/apache‐skywalking‐apm‐bin‐es7/agent/skywalking‐agent.jar
‐DSW_AGENT_COLLECTOR_BACKEND_SERVICES=127.0.0.1:11800
‐DSW_AGENT_NAME=springboot‐skywalking‐demo ‐jar springboot‐skywalking‐demo‐0.0.1‐SNAPSHOT.jar
```

### 开发方式
1. 在idea配置
edit Configurations -> vm options（注意去除注释）
``
# skywalking‐agent.jar的本地磁盘的路径
‐javaagent:D:\MacyDevEnvironment\SPRING_CLOUD_ALIBABA\spring-cloud-alibaba-2.2.7\apache-skywalking-apm-es7-8.7.0\apache-skywalking-apm-bin-es7\agent\skywalking‐agent.jar
# 在skywalking上显示的服务名
‐DSW_AGENT_NAME=skywalking-service
# skywalking的collector服务的IP及端口
‐DSW_AGENT_COLLECTOR_BACKEND_SERVICES=127.0.0.1:11800

``
去除注释后：
```
-javaagent:D:\MacyDevEnvironment\SPRING_CLOUD_ALIBABA\spring-cloud-alibaba-2.2.7\apache-skywalking-apm-es7-8.7.0\apache-skywalking-apm-bin-es7\agent\skywalking-agent.jar
-DSW_AGENT_NAME=skywalking-service
-DSW_AGENT_COLLECTOR_BACKEND_SERVICES=127.0.0.1:11800
```

2. 默认情况下，网关服务不会加入到skywalking，
因此拷贝agent/optional-plugins目录下的gateway插件apm-spring-cloud-gateway-2.1.x-plugin-8.7.0.jar到agent/plugins目录

3. skywalking跨多个服务追踪：每个服务添加javaagent参数

## skywalking持久化跟踪数据
1. 修改config/application.yml文件，把selector: ${SW_STORAGE:mysql}改为mysql,修改mysql连接信息
2. 创建数据库
3. 添加mysql数据驱动包到oap-libs目录下
4. 启动skywalking
5. 从数据库中可以看到生成143张表

## 自定义链路追踪(配置在服务order-seata)
对项目中的业务方法进行链路追踪
1. 引入依赖
```
<!-- SkyWalking 工具类，版本号和下载的skywalking一致-->
<dependency>
    <groupId>org.apache.skywalking</groupId>
    <artifactId>apm-toolkit-trace</artifactId>
    <version>8.7.0</version>
</dependency>
```

2. @Trace将方法加入追踪链路
加入@Tags或@Tag
我们还可以为追踪链路增加其他额外的信息，比如记录参数和返回信息。实现方式：
在方法上增加@Tag或者@Tags。
@Tag 注解中   key  = 方法名   ； value =   returnedObj    返回值   arg[0] 参数
```
@Trace
@Tag(key = "returnValue", value = "returnedObj")
@Tags(@Tag(key = "params",value = "arg[0]"))
public String testSkywalking(int code){
    ProductOrder order = new ProductOrder();
    order.setDescription("创建订单");
    orderRepository.save(order);
    return "创建订单:" + code;
}
```

## skywalking集成日志框架(配置在服务order-seata)
1. 引入依赖
```
<!--  skywalking集成日志框架-->
<dependency>
    <groupId>org.apache.skywalking</groupId>
    <artifactId>apm-toolkit-logback-1.x</artifactId>
    <version>8.7.0</version>
</dependency>
```
2. 添加logback-spring.xml文件,并配置 %tid 占位符
```
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!-- 引入 Spring Boot 默认的 logback XML 配置文件-->
    <include resource="org/springframework/boot/logging/logback/defaults.xml"/>
    <!--    在控制台输出-->
    <!--    https://github.com/apache/skywalking/blob/v8.7.0/docs/en/setup/service-agent/java-agent/Application-toolkit-logback-1.x.md-->
    <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="ch.qos.logback.core.encoder.LayoutWrappingEncoder">
            <layout class="org.apache.skywalking.apm.toolkit.log.logback.v1.x.TraceIdPatternLogbackLayout">
                <Pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%X{tid}] [%thread] %-5level %logger{36} -%msg%n</Pattern>
            </layout>
        </encoder>
    </appender>
    <!-- v8.7.0提供-->
    <!--    把日志发布到skywalking-->
    <appender name="grpc-log" class="org.apache.skywalking.apm.toolkit.log.logback.v1.x.log.GRPCLogClientAppender">
        <encoder class="ch.qos.logback.core.encoder.LayoutWrappingEncoder">
            <layout class="org.apache.skywalking.apm.toolkit.log.logback.v1.x.mdc.TraceIdMDCPatternLogbackLayout">
                <Pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%X{tid}] [%thread] %-5level %logger{36} -%msg%n</Pattern>
            </layout>
        </encoder>
    </appender>
    <!--     设置 Appender-->
     <root level="INFO">
         <appender-ref ref="console"/>
         <appender-ref ref="grpc-log"/>
     </root>
</configuration>
```
3. /agent/config/agent.config文件后面添加
```
plugin.toolkit.log.grpc.reporter.server_host=${SW_GRPC_LOG_SERVER_HOST:127.0.0.1}
plugin.toolkit.log.grpc.reporter.server_port=${SW_GRPC_LOG_SERVER_PORT:11800}
plugin.toolkit.log.grpc.reporter.max_message_size=${SW_GRPC_LOG_MAX_MESSAGE_SIZE:10485760}
plugin.toolkit.log.grpc.reporter.upstream_timeout=${SW_GRPC_LOG_GRPC_UPSTREAM_TIMEOUT:30}
```

## 告警功能（配置在服务stock-nacos）
1. /config/alarm-setting.yml文件配置告警规则
2. 创建接收参数的对象
```
@Data
public class SwAlarmDTO {
    private Integer scopeId;
    private String scope;
    private String name;
    private Integer id0;
    private Integer id1;
    private String ruleName;
    private String alarmMessage;
    private Long startTime;
}
```
3. 创建回调的接口
```
    /**
     * 提供给skywalking告警的回调接口
     * @param alarmList
     */
    @PostMapping("/receive")
    public void receive(@RequestBody List<SwAlarmDTO> alarmList){
        System.out.println("================告警通知=================");
        System.out.println(getContent(alarmList));
        System.out.println("===============告警通知END=================");
    }
    
    private String getContent(List<SwAlarmDTO> alarmList) {
        StringBuilder sb = new StringBuilder();
        for (SwAlarmDTO dto : alarmList) {
            sb.append("scopeId: ").append(dto.getScopeId())
            .append("\nscope: ").append(dto.getScope())
             .append("\n目标 Scope 的实体名称: ").append(dto.getName())
             .append("\nScope 实体的 ID: ").append(dto.getId0())
             .append("\nid1: ").append(dto.getId1())
             .append("\n告警规则名称: ").append(dto.getRuleName())
             .append("\n告警消息内容: ").append(dto.getAlarmMessage())
             .append("\n告警时间: ").append(dto.getStartTime())
             .append("\n\n‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐\n\n");
             }
         return sb.toString();
    }
```
4. 把回调的接口配置到/config/alarm-setting.yml文件
```
webhooks:
 - http://127.0.0.1:9009/stock-service/stock/receive

```

