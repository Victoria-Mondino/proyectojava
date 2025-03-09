package com.finaldejavaMondino.demo.repository;

import com.finaldejavaMondino.demo.model.Comprobante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComprobanteRepository extends JpaRepository<Comprobante, Long> {
}