package com.itmacy.dev.feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 远程服务
 * author: itmacy
 * date: 2022/3/28
 */
@FeignClient(name = "stock-service", path = "/stock")
public interface OrderFeignService {

    @GetMapping("/deduct")
    String deduct();

//    /**
//     * 通过@RuequstHeader注解设置请求头
//     * @param token
//     * @return
//     */
//    @GetMapping("/deduct")
//    String deduct(@RequestHeader(name = "Authorization") String token);
}
