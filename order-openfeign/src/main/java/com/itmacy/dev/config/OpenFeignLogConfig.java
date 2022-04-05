package com.itmacy.dev.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** openfeign日志配置
 * author: itmacy
 * date: 2022/3/28
 */
//@Configuration
public class OpenFeignLogConfig {

    @Bean
    public Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
}
