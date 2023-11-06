package com.novi.carcompany.repositories;

import com.novi.carcompany.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findEmployeesBySurNameIgnoreCaseAndLastNameIgnoreCase(String surName, String lastname);
    List<Employee> findEmployeesBySurNameIgnoreCaseOrLastNameIgnoreCase(String surName, String lastName);
    List<Employee> findEmployeesByFunctionContainingIgnoreCase(String function);
}
