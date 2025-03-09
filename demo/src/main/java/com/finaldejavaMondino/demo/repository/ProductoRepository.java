package com.finaldejavaMondino.demo.repository;

import com.finaldejavaMondino.demo.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}