package com.finaldejavaMondino.demo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class ComprobanteRequest {
    @NotNull(message = "Cliente es requerido")
    @Valid
    private ClienteRequest cliente;

    @NotEmpty(message = "Debe haber al menos una línea")
    @Valid
    private List<LineaRequest> lineas;

    @Data
    public static class ClienteRequest {
        @NotNull(message = "ID de cliente requerido")
        private Long clienteId;
    }

    @Data
    public static class LineaRequest {
        @NotNull(message = "Cantidad requerida")
        @Min(value = 1, message = "Cantidad mínima 1")
        private Integer cantidad;

        @NotNull(message = "Producto requerido")
        @Valid
        private ProductoRequest producto;

        @Data
        public static class ProductoRequest {
            @NotNull(message = "ID de producto requerido")
            private Long productoId;
        }
    }
}