package com.itmacy.dev;

import com.itmacy.dev.config.RibbonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * author: itmacy
 * date: 2022/3/28
 */
@SpringBootApplication
@EnableFeignClients // 开启openfeign
@RibbonClients({  // 配置ribbon负载均衡策略
        @RibbonClient(name = "stock-service", configuration = RibbonConfig.class)
})
public class OrderRibbonApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderRibbonApplication.class, args);
    }
}
