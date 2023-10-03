package com.novi.carcompany.services;

import com.novi.carcompany.dtos.EmployeeDto;
import com.novi.carcompany.dtos.EmployeeInputDto;
import com.novi.carcompany.exceptions.AlreadyExistsException;
import com.novi.carcompany.exceptions.RecordNotFoundException;
import com.novi.carcompany.helpers.DtoConverters;
import com.novi.carcompany.models.Employee;
import com.novi.carcompany.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    ///// For fetching all employees in the database. /////
    public List<EmployeeDto> getEmployees(){
        List<Employee> fetchedEmployees = employeeRepository.findAll();
        List<EmployeeDto> employeeDto = new ArrayList<>();

        if (!fetchedEmployees.isEmpty()) {

            for (Employee employee : fetchedEmployees) {
                EmployeeDto dto = new EmployeeDto();

                DtoConverters.employeeDtoConverter(employee, dto);

                employeeDto.add(dto);
            }
            return employeeDto;
        } else {
            throw new RecordNotFoundException("We hebben geen werknemers in onze database gevonden.");
        }
    }

    ///// For fetching employee by id from the database. /////
    public EmployeeDto getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);


        if (employee.isPresent()) {
            EmployeeDto dto = new EmployeeDto();

            DtoConverters.employeeDtoConverter(employee.get(), dto);

            return dto;
        } else {
            throw new RecordNotFoundException("We hebben geen werknemer met id: " + id + " in onze database.");
        }
    }

    ///// For fetching employee by surname and/or lastname from the database. /////
    public List<EmployeeDto> getEmployeeByName(String surName, String lastName) {
        List<Employee> fetchedEmployeeList = employeeRepository.findEmployeesBySurNameIgnoreCaseOrLastNameIgnoreCase(surName, lastName);
        List<EmployeeDto> employeeList = new ArrayList<>();

        if (!fetchedEmployeeList.isEmpty()) {
            for (Employee employee : fetchedEmployeeList) {
                EmployeeDto dto = new EmployeeDto();

                DtoConverters.employeeDtoConverter(employee, dto);
                employeeList.add(dto);
            }
            return employeeList;
        } else {
            StringBuilder stringBuilder = new StringBuilder();

            if (surName != null) {
                stringBuilder.append(surName);
            }
            if (surName != null && lastName != null) {
                stringBuilder.append(" ");
            }
            if (lastName != null) {
                stringBuilder.append(lastName);
            }
            throw new RecordNotFoundException("We hebben geen werknemers met naam: " + stringBuilder + " gevonden.");
        }
    }

    ///// For fetching employee by function from the database. /////
    public List<EmployeeDto> getEmployeeByFunction(String function) {
        List<Employee> fetchedEmployeeList = employeeRepository.findEmployeesByFunctionContainingIgnoreCase(function);
        List<EmployeeDto> employeeDtoList = new ArrayList<>();

        if (!fetchedEmployeeList.isEmpty()) {

            for (Employee employee : fetchedEmployeeList) {
                EmployeeDto dto = new EmployeeDto();

                DtoConverters.employeeDtoConverter(employee, dto);
                employeeDtoList.add(dto);
            }
            return employeeDtoList;
        } else {
            throw new RecordNotFoundException("We hebben geen werknemers met functie: " + function + " gevonden.");
        }
    }






    ///// For changing an employee in the database. /////

    ///// For deleting an employee from the database. /////

    ///// For adding an employee in the database. /////
    public EmployeeDto createEmployee(EmployeeInputDto employee) {
        Optional<Employee> existingEmployee = employeeRepository.findEmployeesBySurNameIgnoreCaseAndLastNameIgnoreCase(employee.surName, employee.lastName);

        if(existingEmployee.isPresent()) {
            Employee employee1 = existingEmployee.get();
            throw new AlreadyExistsException("We hebben al een werknemer genaamd: " + employee1.getSurName() + " " + employee1.getLastName() + " met id: " + employee1.getId() + " in onze database.");

        } else {
            Employee newEmployee = new Employee();
            EmployeeDto dto = new EmployeeDto();

            DtoConverters.employeeInputDtoConverter(newEmployee, employee);
            employeeRepository.save(newEmployee);

            Optional<Employee> savedEmployee = employeeRepository.findById(newEmployee.getId());
            DtoConverters.employeeDtoConverter(savedEmployee.get(), dto);

            return dto;
        }
    }
}
