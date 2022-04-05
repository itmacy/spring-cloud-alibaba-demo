package com.itmacy.dev.service;

import com.itmacy.dev.entity.ProductOrder;
import com.itmacy.dev.feign.ProductFeignService;
import com.itmacy.dev.repository.OrderRepository;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.skywalking.apm.toolkit.trace.Tag;
import org.apache.skywalking.apm.toolkit.trace.Tags;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: itmacy
 * @date: 2022/4/3
 */
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductFeignService productFeignService;


//    @Transactional // 本地事务
    @GlobalTransactional // 分布式事务
    public String createOrder(){
        ProductOrder order = new ProductOrder();
        order.setDescription("创建订单");
        orderRepository.save(order);
        String deduct = productFeignService.deduct();
        int k=1/0;
        return "创建订单,"+ deduct;
    }


    /**
     * 测试skywalking
     * @return
     */
    @Trace
    @Tag(key = "returnValue", value = "returnedObj")
    @Tags(@Tag(key = "params",value = "arg[0]"))
    public String testSkywalking(int code){
        ProductOrder order = new ProductOrder();
        order.setDescription("创建订单");
        orderRepository.save(order);
        return "创建订单:" + code;
    }
}
