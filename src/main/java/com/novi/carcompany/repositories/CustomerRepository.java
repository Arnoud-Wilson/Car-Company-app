package com.novi.carcompany.repositories;

import com.novi.carcompany.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findCustomersBySurNameIgnoreCaseAndLastNameIgnoreCase(String surName, String lastname);
    List<Customer> findCustomersBySurNameIgnoreCaseOrLastNameIgnoreCase(String surName, String lastName);

    List<Customer> findCustomersByCorporate(Boolean corporate);

}
