package com.itmacy.dev.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义流控规则：一般在服务提供方
 * author: itmacy
 * date: 2022/3/28
 */
@RestController
@RequestMapping("/flow")
public class FlowController {

    @GetMapping("/flow")
    @SentinelResource(value = "/flow/create", blockHandler = "myBlockHandler", fallback = "myBlockHandler")
    public String create(){
        int k = 1/0;
        System.out.println("创建订单！");
        return "创建订单成功:" ;
    }

    /**
     * 定义被流控的方法
     * 方法返回值和参数列表必须与原方法一致，最后参数加上BlockException
     * @param blockException
     * @return
     */
    public String myBlockHandler (BlockException blockException) {
        return "流控处理";
    }

    /**
     * 定义被降级的方法
     * 方法返回值和参数列表必须与原方法一致，最后参数加上Exception
     * @param exception
     * @return
     */
    public String myFallback (Exception exception) {
        return "降级处理";
    }

    /**
     * 自定义流量控制规则
     */
    @PostConstruct
    private void intFlowQpsRule() {
        List<FlowRule> rules = new ArrayList<>();
        // 设置受保护的资源
        FlowRule rule = new FlowRule("/flow/create");
        // 设置流量控制规则
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS); // qps每秒的访问量
        // 设置受保护的阈值
        rule.setCount(3);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }
}
