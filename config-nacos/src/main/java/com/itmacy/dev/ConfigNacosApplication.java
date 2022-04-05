package com.itmacy.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * author: itmacy
 * date: 2022/3/28
 */
@SpringBootApplication
public class ConfigNacosApplication {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(ConfigNacosApplication.class, args);
        // 读取在nacos控制台设置的配置列表，dataId为服务名称，读取properties文件内容
        while (true){
            String username = context.getEnvironment().getProperty("username");
            String password = context.getEnvironment().getProperty("password");
            String desc = context.getEnvironment().getProperty("desc");
            System.out.println(username + ":" + password + ":" + desc);
            Thread.sleep(1000* 3);
        }
    }
}
