package com.finaldejavaMondino.demo.controller;



import com.finaldejavaMondino.demo.middleware.ResponseHandler;
import com.finaldejavaMondino.demo.model.Product;
import com.finaldejavaMondino.demo.service.InvoiceDetailService;
import com.finaldejavaMondino.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/product")
public class ProductController {
    @Autowired
    private ProductService productoService;
    @Autowired
    private InvoiceDetailService invoiceDetailService;

    @PostMapping
    public ResponseEntity<Object> crearProducto (@RequestBody Product producto) {
        try {
            Product productSaved = productoService.crearProducto(producto);
            return ResponseHandler.generateResponse(
                    "Product saved successfully",
                    HttpStatus.OK,
                    productSaved
            );
        } catch (Exception e){
            return ResponseHandler.generateResponse(
                    e.getMessage(),
                    HttpStatus.OK,
                    null
            );
        }
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Object> obtenerProducto (@PathVariable int id){
        try {
            Product productFound = productoService.obtenerProducto(id);
            return ResponseHandler.generateResponse(
                    "Product get successfully",
                    HttpStatus.OK,
                    productFound
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    e.getMessage(),
                    HttpStatus.OK,
                    null
            );
        }
    }
    @GetMapping
    public ResponseEntity<Object> getAll() {
        try {
            List<Product> products = productoService.getAll();
            return ResponseHandler.generateResponse(
                    "Product get successfully",
                    HttpStatus.OK,
                    products
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    e.getMessage(),
                    HttpStatus.OK,
                    null
            );
        }
    }

    @PutMapping(path = "{product_id}")
    public ResponseEntity<Object> modificarProducto (
            @PathVariable("product_id") int id,
            @RequestBody Product product
    ) {
        try {

            productoService.modificarProducto (product, id);
            return ResponseHandler.generateResponse(
                    "Product data updated successfully",
                    HttpStatus.OK,
                    null
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    e.getMessage(),
                    HttpStatus.OK,
                    null
            );
        }
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Object> borrarProducto (@PathVariable() int id) {
        try {
            invoiceDetailService.nullProduct(id);
            productoService.borrarProducto(id);
            return ResponseHandler.generateResponse(
                    "Product deleted successfully",
                    HttpStatus.OK,
                    null
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    e.getMessage(),
                    HttpStatus.OK,
                    null
            );
        }
    }
}