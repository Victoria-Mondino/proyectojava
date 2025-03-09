package com.finaldejavaMondino.demo.service;

import com.finaldejavaMondino.demo.dto.*;
import com.finaldejavaMondino.demo.model.*;
import com.finaldejavaMondino.demo.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ComprobanteService {

    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final ComprobanteRepository comprobanteRepository;
    private final DateService dateService;

    public ComprobanteResponse generarComprobante(ComprobanteRequest request) {
        ComprobanteResponse response = new ComprobanteResponse();
        List<String> errores = new ArrayList<>();

        // Validar cliente
        Cliente cliente = clienteRepository.findById(request.getCliente().getClienteId())
                .orElseGet(() -> {
                    errores.add("Cliente no encontrado");
                    return null;
                });

        List<Linea> lineasValidas = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        int cantidadProductos = 0;

        if (cliente != null) {
            for (ComprobanteRequest.LineaRequest lineaReq : request.getLineas()) {
                Producto producto = productoRepository.findById(lineaReq.getProducto().getProductoId())
                        .orElseGet(() -> {
                            errores.add("Producto ID " + lineaReq.getProducto().getProductoId() + " no encontrado");
                            return null;
                        });

                if (producto != null) {
                    if (producto.getStock() < lineaReq.getCantidad()) {
                        errores.add("Stock insuficiente para: " + producto.getNombre() +
                                " (Stock actual: " + producto.getStock() + ")");
                    } else {
                        Linea linea = new Linea();
                        linea.setCantidad(lineaReq.getCantidad());
                        linea.setProducto(producto);
                        linea.setPrecioUnitario(producto.getPrecio());

                        lineasValidas.add(linea);
                        total = total.add(producto.getPrecio().multiply(BigDecimal.valueOf(lineaReq.getCantidad())));
                        cantidadProductos += lineaReq.getCantidad();

                        producto.setStock(producto.getStock() - lineaReq.getCantidad());
                        productoRepository.save(producto);
                    }
                }
            }
        }

        if (!errores.isEmpty()) {
            response.setErrores(errores);
            return response;
        }

        Comprobante comprobante = new Comprobante();
        comprobante.setCliente(cliente);
        comprobante.setFecha(dateService.obtenerFecha());
        comprobante.setTotal(total);
        comprobante.setCantidadProductos(cantidadProductos);
        comprobante.setLineas(lineasValidas);

        lineasValidas.forEach(linea -> linea.setComprobante(comprobante));
        Comprobante comprobanteGuardado = comprobanteRepository.save(comprobante);

        return mapearResponse(comprobanteGuardado);
    }

    private ComprobanteResponse mapearResponse(Comprobante comprobante) {
        ComprobanteResponse response = new ComprobanteResponse();
        response.setComprobanteId(comprobante.getComprobanteId());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        response.setFecha(sdf.format(comprobante.getFecha()));

        response.setTotal(comprobante.getTotal());
        response.setCantidadProductos(comprobante.getCantidadProductos());

        ComprobanteResponse.ClienteResponse clienteResponse = new ComprobanteResponse.ClienteResponse();
        clienteResponse.setClienteId(comprobante.getCliente().getClienteId());
        clienteResponse.setNombre(comprobante.getCliente().getNombre());
        response.setCliente(clienteResponse);

        response.setLineas(comprobante.getLineas().stream().map(linea -> {
            ComprobanteResponse.LineaResponse lr = new ComprobanteResponse.LineaResponse();
            lr.setProductoNombre(linea.getProducto().getNombre());
            lr.setCantidad(linea.getCantidad());
            lr.setPrecioUnitario(linea.getPrecioUnitario());
            lr.setSubtotal(linea.getPrecioUnitario().multiply(BigDecimal.valueOf(linea.getCantidad())));
            return lr;
        }).collect(Collectors.toList()));

        return response;
    }
}