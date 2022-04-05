package com.itmacy.dev.repository;

import com.itmacy.dev.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: itmacy
 * @date: 2022/4/3
 */
public interface ProductRepository extends JpaRepository<Product,Integer> {
}
