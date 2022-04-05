package com.itmacy.dev.service;

import com.itmacy.dev.entity.Product;
import com.itmacy.dev.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: itmacy
 * @date: 2022/4/3
 */
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;


    public String deduct(){
        Product product = productRepository.getOne(1);
        product.setStock(product.getStock() - 1);
        productRepository.save(product);
        return "库存扣除成功";
    }
}
