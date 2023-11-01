package com.novi.carcompany.controllers;

import com.novi.carcompany.dtos.EmployeeDto;
import com.novi.carcompany.dtos.InvoiceDto;
import com.novi.carcompany.dtos.InvoiceInputDto;
import com.novi.carcompany.services.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @GetMapping("/{invoiceNumber}")
    ResponseEntity<InvoiceDto> getInvoiceByInvoiceNumber(@PathVariable Long invoiceNumber) {
        return ResponseEntity.ok(invoiceService.getInvoiceByInvoiceNumber(invoiceNumber));
    }

    @GetMapping("/unpaid")
    ResponseEntity<List<InvoiceDto>> getAllUnpaidInvoices() {
        return ResponseEntity.ok(invoiceService.getAllUnpaidInvoices());
    }

    @PostMapping
    ResponseEntity<Object> createInvoice(@Valid @RequestBody InvoiceInputDto invoice, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                stringBuilder.append(fieldError.getField());
                stringBuilder.append(": ");
                stringBuilder.append(fieldError.getDefaultMessage());
                stringBuilder.append("\n");
            }
            return ResponseEntity.badRequest().body(stringBuilder);
        } else {
            InvoiceDto invoiceDto = invoiceService.createInvoice(invoice);
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + invoiceDto.invoiceNumber).toUriString());

            return ResponseEntity.created(uri).body(invoiceDto);
        }
    }

    @PutMapping("/{invoiceNumber}")
    ResponseEntity<InvoiceDto> changeInvoice(@PathVariable Long invoiceNumber, @RequestBody InvoiceDto invoice) {
        InvoiceDto dto = invoiceService.changeInvoice(invoiceNumber, invoice);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());

        return ResponseEntity.created(uri).body(dto);
    }











    //TODO: make get invoice by customer and by car if relations are done!

}
