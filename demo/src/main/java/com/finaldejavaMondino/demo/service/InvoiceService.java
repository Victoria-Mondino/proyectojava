package com.finaldejavaMondino.demo.service;

import com.finaldejavaMondino.demo.model.*;
import com.finaldejavaMondino.demo.repository.ClientRepository;
import com.finaldejavaMondino.demo.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private InvoiceDetailService invoiceDetailService;

    @Autowired
    private ClientService clientService;
    public InvoiceDTO postInvoice (InvoiceRequest requestInvoice) throws Exception {

        Client clientExist = clientService.obtenerCliente(requestInvoice.getClient_id());
        List<Product> productList = productService.getProductsById(requestInvoice.getProduct_list());
        int i = 0;
        for (Product productStock:
                productList) {
            if (requestInvoice.getProduct_list().get(i).getQuantity() > productStock.getStock()){
                throw new Exception("Not enough stock");
            }
            i++;
        }

        int j = 0;
        double total = 0;
        for (Product product:
                productList) {
            total += product.getPrice() * requestInvoice.getProduct_list().get(j).getQuantity();
            int newStock = product.getStock() - requestInvoice.getProduct_list().get(j).getQuantity();
            product.setStock(newStock);
            j++;
        }

        Invoice invoiceCreated = new Invoice();
        invoiceCreated.setCreated_at(new Date().toString());
        invoiceCreated.setClient(clientExist);
        invoiceCreated.setTotal(total);
        invoiceCreated = invoiceRepository.save(invoiceCreated);

        i = 0;
        for (Product productForDetail:
                productList) {
            InvoiceDetail newInvoice = new InvoiceDetail();
            newInvoice.setPrice(productForDetail.getPrice());
            newInvoice.setInvoice(invoiceCreated);
            newInvoice.setProduct(productForDetail);
            newInvoice.setQuantity(requestInvoice.getProduct_list().get(i).getQuantity());
            invoiceDetailService.saveInvoiceDetail(newInvoice);
            i++;
        }

        return new InvoiceDTO(
                invoiceCreated.getId(),
                invoiceCreated.getCreated_at(),
                invoiceCreated.getTotal()
        );
    }

    public List<InvoiceDTO> getInvoicesByClientId (int clientId) throws Exception {
        return invoiceRepository.getInvoicesByClientById(clientId);
    }

    public InvoiceWithDetailsDTO getInvoiceById (int invoice_id) throws Exception {
        Optional<Invoice> invoiceFound = invoiceRepository.findById(invoice_id);
        if (invoiceFound.isEmpty()) {
            throw new Exception("Invoice with id: " + invoice_id + " not found.");
        }

        List<InvoiceDetailDTO> invoice_details = invoiceDetailService.getInvoiceDetailsByInvoiceId(invoice_id);

        return new InvoiceWithDetailsDTO(
                invoiceFound.get().getId(),
                invoiceFound.get().getClient().getId(),
                invoiceFound.get().getCreated_at(),
                invoiceFound.get().getTotal(),
                invoice_details
        );

    }
    public void nullClient(int id) throws Exception {
        List<InvoiceDTO> invoiceList = getInvoicesByClientId(id);
        if (invoiceList.isEmpty()){
            System.out.println("OK");
        } else {
            for (InvoiceDTO invoice :
                    invoiceList) {
                int invoiceID = invoice.getInvoice_id();
                Optional<Invoice> invoiceMod = invoiceRepository.findById(invoiceID);
                invoiceMod.get().setClient(null);
            }
        }
    }
}