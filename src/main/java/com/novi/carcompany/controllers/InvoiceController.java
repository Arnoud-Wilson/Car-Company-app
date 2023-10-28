package com.novi.carcompany.controllers;

import com.novi.carcompany.dtos.InvoiceDto;
import com.novi.carcompany.services.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    //get all invoices
    //get invoice by id
    //get invoices by customer
    //get invoices by car
    //get all unpaid invoices
    //post invoice
    //put invoice
    //delete invoice

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }


    @GetMapping
    public ResponseEntity<List<InvoiceDto>> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }







}
