package com.finaldejavaMondino.demo.model;



import java.util.List;

public class InvoiceRequest {
    private int client_id;
    private List<ProductDetailRequest> product_list;

    public InvoiceRequest(int client_id, List<ProductDetailRequest> product_list) {
        this.client_id = client_id;
        this.product_list = product_list;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public List<ProductDetailRequest> getProduct_list() {
        return product_list;
    }

    public void setProduct_list(List<ProductDetailRequest> product_list) {
        this.product_list = product_list;
    }

    @Override
    public String toString() {
        return "InvoiceRequest{" +
                "client_id=" + client_id +
                ", product_list=" + product_list +
                '}';
    }
}