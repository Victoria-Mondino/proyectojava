package com.finaldejavaMondino.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clienteId;

    private String nombre;
    private String email;
    private String direccion;
    private String telefono;

    @Column(updatable = false)
    private LocalDateTime fechaRegistro;
}