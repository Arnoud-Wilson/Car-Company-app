package com.novi.carcompany.repositories;

import com.novi.carcompany.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Boolean existsBySurNameIgnoreCaseAndLastNameIgnoreCase(String surName, String lastName);
    Optional<Employee> findEmployeesBySurNameIgnoreCaseAndLastNameIgnoreCase(String surName, String lastname);

}
