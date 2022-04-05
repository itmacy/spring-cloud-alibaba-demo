package com.itmacy.dev.entity;

import javax.persistence.*;

/**
 * @author: itmacy
 * @date: 2022/4/3
 */
@Entity
@Table
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private int stock;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
