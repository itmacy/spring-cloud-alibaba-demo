package com.itmacy.dev.feign;

import com.itmacy.dev.config.OpenFeignLogConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * openfeign日志局部调用
 * author: itmacy
 * date: 2022/3/28
 */
// configuration ：配置局部日志，OpenFeignLogConfig类上不能加注解@Configuration
//@FeignClient(name = "stock-service1", path = "/stock", configuration = OpenFeignLogConfig.class)
@FeignClient(name = "stock-service1", path = "/stock") // 为了避免复制一个项目，这里使用Services复制一个运行实例
public interface OrderFeignLogService {

    @GetMapping("/deduct")
    String deduct();
}
