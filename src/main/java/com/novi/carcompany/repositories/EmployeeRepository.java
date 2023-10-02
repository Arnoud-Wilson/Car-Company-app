package com.novi.carcompany.repositories;

import com.novi.carcompany.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
