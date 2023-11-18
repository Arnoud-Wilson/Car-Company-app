package com.novi.carcompany.controllers.businessEntities;

import com.novi.carcompany.dtos.businessEntities.InvoiceDto;
import com.novi.carcompany.dtos.businessEntities.InvoiceInputDto;
import com.novi.carcompany.dtos.businessEntities.NumberInputDto;
import com.novi.carcompany.dtos.businessEntities.StringInputDto;
import com.novi.carcompany.helpers.BindingResults;
import com.novi.carcompany.services.businessEntities.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/invoices")
public class InvoiceController {

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

    @GetMapping("/customer/{customerId}")
    ResponseEntity<List<InvoiceDto>> getCustomerInvoices(@PathVariable Long customerId) {
        return ResponseEntity.ok(invoiceService.getCustomerInvoices(customerId));
    }

    @PostMapping
    ResponseEntity<Object> createInvoice(@Valid @RequestBody InvoiceInputDto invoice, BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors()) {
            return  BindingResults.showBindingResult(bindingResult);
        } else {
            InvoiceDto invoiceDto = invoiceService.createInvoice(invoice);
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + invoiceDto.invoiceNumber).toUriString());

            return ResponseEntity.created(uri).body(invoiceDto);
        }
    }

    @PutMapping("/{invoiceNumber}")
    ResponseEntity<Object> changeInvoice(@PathVariable Long invoiceNumber, @Valid @RequestBody InvoiceDto invoice, BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors()) {
            return BindingResults.showBindingResult(bindingResult);
        } else {
            InvoiceDto dto = invoiceService.changeInvoice(invoiceNumber, invoice);
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());

            return ResponseEntity.created(uri).body(dto);
        }
    }

    @PutMapping("/{invoiceNumber}/employee")
    ResponseEntity<Object> assignEmployeeToInvoice(@PathVariable Long invoiceNumber, @Valid @RequestBody NumberInputDto employeeId, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return  BindingResults.showBindingResult(bindingResult);
        } else {

            InvoiceDto dto = invoiceService.assignEmployeeToInvoice(invoiceNumber, employeeId);
            URI uri = URI.create("http://localhost:8080/invoices/" + invoiceNumber);

            return ResponseEntity.created(uri).body(dto);
        }
    }

    @PutMapping("/{invoiceNumber}/customer")
    ResponseEntity<Object> assignCustomerToInvoice(@PathVariable Long invoiceNumber, @Valid @RequestBody NumberInputDto customerId, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return  BindingResults.showBindingResult(bindingResult);
        } else {

            InvoiceDto dto = invoiceService.assignCustomerToInvoice(invoiceNumber, customerId);
            URI uri = URI.create("http://localhost:8080/invoices/" + invoiceNumber);

            return ResponseEntity.created(uri).body(dto);
        }
    }

    @PutMapping("/{invoiceNumber}/car")
    ResponseEntity<Object> assignCarToInvoice(@PathVariable Long invoiceNumber, @Valid @RequestBody StringInputDto licensePlate, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return  BindingResults.showBindingResult(bindingResult);
        } else {

            InvoiceDto dto = invoiceService.assignCarToInvoice(invoiceNumber, licensePlate);
            URI uri = URI.create("http://localhost:8080/invoices/" + invoiceNumber);

            return ResponseEntity.created(uri).body(dto);
        }
    }

    @PutMapping("/{invoiceNumber}/part")
    ResponseEntity<Object> assignPartToInvoice(@PathVariable Long invoiceNumber, @Valid @RequestBody StringInputDto partNumber, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return  BindingResults.showBindingResult(bindingResult);
        } else {

            InvoiceDto dto = invoiceService.assignPartToInvoice(invoiceNumber, partNumber);
            URI uri = URI.create("http://localhost:8080/invoices/" + invoiceNumber);

            return ResponseEntity.created(uri).body(dto);
        }
    }

    @DeleteMapping("/{invoiceNumber}")
        public ResponseEntity<String> deleteInvoice(@PathVariable Long invoiceNumber) {
            InvoiceDto dto = invoiceService.deleteInvoice(invoiceNumber);
            return ResponseEntity.ok().body("We hebben factuur met nummer: " + dto.invoiceNumber + " uit de database verwijderd.");
        }
}
