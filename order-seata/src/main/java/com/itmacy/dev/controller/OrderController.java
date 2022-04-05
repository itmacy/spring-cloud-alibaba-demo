package com.itmacy.dev.controller;

import com.itmacy.dev.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private OrderService orderService;

    /**
     * 测试远程调用
     * @return
     */
    @GetMapping("/create")
    public String create(){
        System.out.println("创建订单！");
        return orderService.createOrder();

    }


    /**
     * 测试skywalking
     * @return
     */
    @GetMapping("/testsk/{code}")
    public String testSkywalking(@PathVariable("code") int code){
        System.out.println("创建订单！");
        return orderService.testSkywalking(code);

    }
}
