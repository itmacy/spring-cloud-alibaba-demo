package com.itmacy.dev.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 远程服务
 * author: itmacy
 * date: 2022/3/28
 */
@FeignClient(name = "stock-service", path = "/stock")
public interface OrderFeignService {

    @GetMapping("/deduct")
    String deduct();
}
