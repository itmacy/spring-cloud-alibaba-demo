package com.itmacy.dev.config;

import com.alibaba.cloud.nacos.ribbon.NacosRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * author: itmacy
 * date: 2022/3/29
 */
@Configuration
public class RibbonConfig {

    /**
     * 修改负载均衡策略
     * RandomRule: 随机选择一个服务实例
     * RoundRobinRule： 轮询负载均衡策略
     * RetryRule: 具有重试功能
     * WeightedResponseTimeRule: 权重
     * BestAvailableRule: 过滤失效的服务实例，找出并发请求最小的服务实例
     * ZoneAvoidanceRule：默认规则，根据区域+轮询
     * @return
     */
    @Bean
    public IRule iRule() {
        return new NacosRule();
    }
}
