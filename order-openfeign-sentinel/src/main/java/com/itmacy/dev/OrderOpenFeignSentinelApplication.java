package com.itmacy.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * author: itmacy
 * date: 2022/3/28
 */
@SpringBootApplication
@EnableFeignClients
public class OrderOpenFeignSentinelApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderOpenFeignSentinelApplication.class, args);
    }
}
