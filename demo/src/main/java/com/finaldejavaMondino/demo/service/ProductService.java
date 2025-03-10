package com.finaldejavaMondino.demo.service;



import com.finaldejavaMondino.demo.model.Product;
import com.finaldejavaMondino.demo.model.ProductDetailRequest;
import com.finaldejavaMondino.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productoRepository;
    public Product crearProducto(Product producto) throws Exception {
        return productoRepository.save(producto);
    }
    public Product obtenerProducto(int id) throws Exception {
        Optional<Product> producto = productoRepository.findById(id);
        if(producto.isEmpty()){
            throw new Exception("Product with id: " + id + " not found.");
        } else {
            return producto.get();
        }
    }
    public List<Product> getAll() {
        return productoRepository.findAll();
    }
    public List<Product> getProductsById(List<ProductDetailRequest> productListId) throws Exception {
        List<Product> productList = new ArrayList<>();
        for (ProductDetailRequest requestProduct:
                productListId) {
            Optional<Product> productFound = productoRepository.findById(requestProduct.getProductId());
            if (productFound.isEmpty()){
                throw new Exception("Product with id: " + requestProduct.getProductId() + " not found.");
            }
            productList.add(productFound.get());
        }
        return productList;
    }

    public void modificarProducto(Product product, int id) throws Exception {
        Optional<Product> productoExistente = productoRepository.findById(id);
        if(productoExistente.isEmpty()){
            throw new Exception("Product with id: " + id + " not found.");
        } else {
            productoExistente.get().setTitle(product.getTitle());
            productoExistente.get().setDescription(product.getDescription());
            productoExistente.get().setCode(product.getCode());
            productoExistente.get().setPrice(product.getPrice());
            productoExistente.get().setStock(product.getStock());
            productoRepository.save(productoExistente.get());
        }
    }

    public void borrarProducto (int id) throws Exception {
        Optional<Product> productoExistente = productoRepository.findById(id);
        if(productoExistente.isEmpty()){
            throw new Exception("Product with id: " + id + " not found.");
        } else {
            productoRepository.deleteById(id);
        }
    }
}