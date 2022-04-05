## Seata
用于处理分布式事务
产生分布式事务的原因：
- 每个服务连接不同的数据库
- 每个服务连接同一个数据库
- 一个服务调用多个数据库 
### 服务器端
1. 下载与spring cloud alibaba对应的版本：https://github.com/seata/seata/releases
2. 配置数据源为db（file.conf）
    - 把mode改为db,并修改数据库连接
    - 创建数据库
    - 创建数据库表，获取sql语句：https://github.com/seata/seata/blob/1.4.0/script/server/db/mysql.sql
3. 修改注册中心和配置中心的信息(register.conf):
    - 把type都改为nacos
    - 修改注册中心和配置中心
4.把配置信息保存在nacos
    - 下载源码，把script文件夹放入seata目录下
    - 修改数据源/seata/script/config-center/config.txt：修改mode为db,以及数据库连接信息，关于事务分组：service.vgroupMapping.my_test_tx_group=default，my_test_tx_group是事务分组
    的名称，default必须对应registry.conf文件下的cluster="default"
    - 把配置信息注册到nacos配置中心：运行/seata/script/config-center/nacos里面的运行文件：nacos-config.sh(使用git双击运行)
    - 查看nacos控制台配置中心，可以看到多条数据
5. 运行seata：双击运行seata/bin/seata-server.bat
   当启动失败时；
   - 无法连接mysql:如果mysql版本过高，则修改驱动为com.mysql.cj.jdbc.Driver
   - 如果报时区乱码，则添加&serverTimezone=Asia/Shanghai，查看nacos控制台配置中心，如果没有同步成功，则手动在控制台修改
### 客户端
1. 引入依赖
```
        <!-- seata依赖-->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-seata</artifactId>
        </dependency>
```
2. 每个服务的数据库加上undo_log表
- 配置yml
```
    schema: classpath:sql/undo_log.sql # 初始化时表结构
    initialization-mode: always  # always每次都会自动生成,never从不，第一次生成表时需要改为always
```
- 在resource/sql下添加undo_log.sql文件
```
CREATE TABLE `undo_log` (
	`id` BIGINT ( 20 ) NOT NULL AUTO_INCREMENT,
	`branch_id` BIGINT ( 20 ) NOT NULL,
	`xid` VARCHAR ( 100 ) NOT NULL,
	`context` VARCHAR ( 128 ) NOT NULL,
	`rollback_info` LONGBLOB NOT NULL,
	`log_status` INT ( 11 ) NOT NULL,
	`log_created` datetime NOT NULL,
	`log_modified` datetime NOT NULL,
	PRIMARY KEY ( `id` ),
UNIQUE KEY `ux_undo_log` ( `xid`, `branch_id` )
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8;
```
3. yml配置
```
spring:
  cloud:
    alibaba:
      seata:
        tx-service-group: my_text_tx_group  # 设置seata事务分组，与seata服务端seata/script/config-center/service.vgroupMapping.my_test_tx_group=default一致
seata:
  registry:
    # 配置seata的注册中心，告诉seata client 怎么去访问seata server
    type: nacos
    nacos:
      server-addr: 127.0.0.1:8848 # nacos服务器地址
      application: seata-server # seata server的服务名，默认是seata-server，没有修改可以不配
      username: nacos
      password: nacos
      group: SEATA_GROUP # seata分组，默认是SEATA_GROUP，没有修改可以不配
  config:
    # 配置seata的配置中心，可以读取seata client的一些配置
    type: nacos
    nacos:
      server-addr: 127.0.0.1:8848
      username: nacos
      password: nacos
      group: SEATA_GROUP # seata分组，默认是SEATA_GROUP，没有修改可以不配
```
4. 使用分布式事务注解:@GlobalTransactional