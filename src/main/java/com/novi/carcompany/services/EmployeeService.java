package com.novi.carcompany.services;

import com.novi.carcompany.dtos.EmployeeDto;
import com.novi.carcompany.dtos.EmployeeInputDto;
import com.novi.carcompany.dtos.PartDto;
import com.novi.carcompany.exceptions.AlreadyExistsException;
import com.novi.carcompany.exceptions.RecordNotFoundException;
import com.novi.carcompany.helpers.DtoConverters;
import com.novi.carcompany.models.Employee;
import com.novi.carcompany.models.Part;
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
