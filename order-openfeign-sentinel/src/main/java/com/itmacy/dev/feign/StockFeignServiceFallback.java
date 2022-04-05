package com.itmacy.dev.feign;

import org.springframework.stereotype.Service;

/**
 * 降级服务
 */
@Service
public class StockFeignServiceFallback implements StockFeignService{
    @Override
    public String deduct() {
        return "降级服务";
    }
}
