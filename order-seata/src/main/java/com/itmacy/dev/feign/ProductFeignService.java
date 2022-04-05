package com.itmacy.dev.feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 远程服务
 * author: itmacy
 * date: 2022/3/28
 */
@FeignClient(name = "product-service", path = "/product")
public interface ProductFeignService {

    @GetMapping("/deduct")
    String deduct();

}
