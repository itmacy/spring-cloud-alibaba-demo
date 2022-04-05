package com.itmacy.dev.entity;

import javax.persistence.*;

/**
 * @author: itmacy
 * @date: 2022/4/3
 */
@Entity
@Table
public class ProductOrder {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String description;

    public ProductOrder(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public ProductOrder() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
