package com.finaldejavaMondino.demo.repository;

import com.finaldejavaMondino.demo.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}