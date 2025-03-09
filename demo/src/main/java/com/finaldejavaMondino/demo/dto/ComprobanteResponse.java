package com.finaldejavaMondino.demo.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ComprobanteResponse {
    private Long comprobanteId;
    private String fecha;
    private BigDecimal total;
    private Integer cantidadProductos;
    private List<String> errores;
    private ClienteResponse cliente;
    private List<LineaResponse> lineas;

    @Data
    public static class ClienteResponse {
        private Long clienteId;
        private String nombre;
    }

    @Data
    public static class LineaResponse {
        private String productoNombre;
        private Integer cantidad;
        private BigDecimal precioUnitario;
        private BigDecimal subtotal;
    }
}