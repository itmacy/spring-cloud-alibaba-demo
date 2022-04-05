package com.itmacy.dev.controller;

import com.itmacy.dev.feign.OrderFeignLogService;
import com.itmacy.dev.feign.OrderFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: itmacy
 * date: 2022/3/28
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderFeignService orderFeignService;
    @Autowired
    private OrderFeignLogService orderFeignLogService;

    /**
     * 测试远程调用
     * @return
     */
    @GetMapping("/create")
    public String create(){
        System.out.println("创建订单！");
        String msg = orderFeignService.deduct();
        return "创建订单成功:" + msg;
    }

    /**
     * 测试局部日志
     * @return
     */
    @GetMapping("/test")
    public String test(){
        System.out.println("创建订单！");
        String msg = orderFeignService.deduct();
        System.out.println("===============================");
        orderFeignLogService.deduct();
        return "创建订单成功:" + msg;
    }
}
