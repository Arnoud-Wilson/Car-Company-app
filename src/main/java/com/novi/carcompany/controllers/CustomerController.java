package com.novi.carcompany.controllers;

import com.novi.carcompany.dtos.CustomerDto;
import com.novi.carcompany.dtos.CustomerInputDto;
import com.novi.carcompany.helpers.BindingResults;
import com.novi.carcompany.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long id) {

        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @GetMapping("/name")
    public ResponseEntity<List<CustomerDto>> getCustomerByName(@RequestParam(required = false) String surname, String lastname) {
        return ResponseEntity.ok(customerService.getCustomerByName(surname, lastname));
    }

    @GetMapping("/corporate")
    public ResponseEntity<List<CustomerDto>> getAllCorporateOrPrivate(@RequestParam(required = true) Boolean corporate) {
        return ResponseEntity.ok(customerService.getAllCorporateOrPrivate(corporate));
    }

    @PostMapping
    public ResponseEntity<Object> createCustomer(@Valid @RequestBody CustomerInputDto customer, BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors()) {
            return  BindingResults.showBindingResult(bindingResult);
        } else {
            CustomerDto dto = customerService.createCustomer(customer);
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + dto.id).toUriString());

            return ResponseEntity.created(uri).body(dto);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> changeCustomer(@PathVariable Long id, @Valid @RequestBody CustomerDto customer, BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors()) {
            return BindingResults.showBindingResult(bindingResult);
        } else {
            CustomerDto dto = customerService.changeCustomer(id, customer);
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());

            return ResponseEntity.created(uri).body(dto);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        CustomerDto dto = customerService.deleteCustomer(id);
        return ResponseEntity.ok().body("We hebben klant " + dto.surName + " " + dto.lastName + " met id: " + dto.id + " uit de database verwijderd.");
    }
}
