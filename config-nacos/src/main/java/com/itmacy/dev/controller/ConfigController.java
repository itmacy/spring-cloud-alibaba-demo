package com.itmacy.dev.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: itmacy
 * date: 2022/3/29
 */
@RestController
@RequestMapping("config")
@RefreshScope
public class ConfigController {

    @Value("${username}")
    String username;
    @Value("${password}")
    String password;
    @Value("${desc}")
    String desc;

    @GetMapping("test")
    public String test(){
        return username + ":" + password + ":" + desc;
    }
}
