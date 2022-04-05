package com.itmacy.dev.controller;

import com.itmacy.dev.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: itmacy
 * date: 2022/3/28
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 测试远程调用
     * @return
     */
    @GetMapping("/deduct")
    public String deduct(){
        System.out.println("扣除库存！");
        return productService.deduct();

    }
}
