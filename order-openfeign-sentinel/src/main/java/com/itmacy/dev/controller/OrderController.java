package com.itmacy.dev.controller;

import com.itmacy.dev.feign.StockFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * sentinel统一异常处理，流控规则和熔断降级在sentinel dashboard设置
 * author: itmacy
 * date: 2022/3/28
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private StockFeignService stockFeignService;

    /**
     * 测试远程调用
     * @return
     */
    @GetMapping("/create")
    public String create(){
        System.out.println("创建订单！");
        String msg = stockFeignService.deduct();
        return "创建订单成功:" + msg;
    }
}
