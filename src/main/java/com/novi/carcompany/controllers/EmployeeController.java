package com.novi.carcompany.controllers;

import com.novi.carcompany.dtos.EmployeeDto;
import com.novi.carcompany.dtos.EmployeeInputDto;
import com.novi.carcompany.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {

            return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping("/name")
    public ResponseEntity<List<EmployeeDto>> getEmployeeByName(@RequestParam(required = false) String surname, String lastname) {
        return ResponseEntity.ok(employeeService.getEmployeeByName(surname, lastname));
    }

    @GetMapping("/function")
    public List<EmployeeDto> getEmployeeByFunction(@RequestParam(required = false) String function) {
        return employeeService.getEmployeeByFunction(function);
    }

    @PostMapping
    public ResponseEntity<Object> createEmployee(@Valid @RequestBody EmployeeInputDto employee, BindingResult bindingResult) {

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
            EmployeeDto dto = employeeService.createEmployee(employee);
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + dto.id).toUriString());

            return ResponseEntity.created(uri).body(dto);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> changeEmployee(@PathVariable Long id, @RequestBody EmployeeDto employee) {

        EmployeeDto dto = employeeService.changeEmployee(id, employee);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());

        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        EmployeeDto dto = employeeService.deleteEmployee(id);
        return ResponseEntity.ok().body("We hebben werknemer " + dto.surName + " " + dto.lastName + " met id: " + dto.id + " uit de database verwijderd.");
    }
}
