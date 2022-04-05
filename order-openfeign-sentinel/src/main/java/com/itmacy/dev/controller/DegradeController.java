package com.itmacy.dev.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义熔断降级规则 ：一般是在服务消费方
 * author: itmacy
 * date: 2022/3/28
 */
@RestController
@RequestMapping("/degrade")
public class DegradeController {

    /**
     * 测试远程调用
     * @return
     */
    @GetMapping("/create")
    @SentinelResource(value = "/degrade/create", blockHandler = "myBlockHandler")
    public String create(){
        int k = 1/0;
        System.out.println("创建订单！");
        return "创建订单成功:";
    }

    /**
     * 定义被熔断降级的方法
     * 方法返回值和参数列表必须与原方法一致，最后参数加上BlockException
     * @param blockException
     * @return
     */
    public String myBlockHandler (BlockException blockException) {
        return "降级处理";
    }


    /**
     * 自定义熔断降级规则
     */
    @PostConstruct
    private void intDegradeRule() {
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        // 熔断降级的资源
        rule.setResource("/degrade/create");
        // 熔断策略
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT); // 出现异常次数
        // 触发熔断异常数
        rule.setCount(2);
        // 触发熔断最小请求数
        rule.setMinRequestAmount(2);
        // 统计时长
        rule.setStatIntervalMs(1000 * 6);
        // 触发条件：一分钟内，2个请求以上出现2个异常
        // 时间窗口：满足熔断的条件后，如果还是出现异常则立即降级，在时间窗口内，不再根据熔断的条件
        rule.setTimeWindow(10); // 单位s
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }
}
