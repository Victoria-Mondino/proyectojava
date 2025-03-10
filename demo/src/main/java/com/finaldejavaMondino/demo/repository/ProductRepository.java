package com.finaldejavaMondino.demo.repository;


import com.finaldejavaMondino.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository <Product, Integer> {
    List<Product> findAll();
}
