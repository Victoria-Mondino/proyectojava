package com.finaldejavaMondino.demo.repository;

import com.finaldejavaMondino.demo.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository <Client, Integer> {
}
