package com.finaldejavaMondino.demo.model;


public class ProductDetailRequest {
    private int productId;
    private int quantity;

    public ProductDetailRequest(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProductDetailRequest{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
