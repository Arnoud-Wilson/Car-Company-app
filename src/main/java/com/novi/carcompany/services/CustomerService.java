package com.novi.carcompany.services;

import com.novi.carcompany.dtos.CustomerDto;
import com.novi.carcompany.dtos.CustomerInputDto;
import com.novi.carcompany.dtos.EmployeeDto;
import com.novi.carcompany.dtos.EmployeeInputDto;
import com.novi.carcompany.exceptions.AlreadyExistsException;
import com.novi.carcompany.exceptions.RecordNotFoundException;
import com.novi.carcompany.helpers.DtoConverters;
import com.novi.carcompany.models.Customer;
import com.novi.carcompany.models.Employee;

import com.novi.carcompany.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    ///// For fetching all customers in the database. /////
    public List<CustomerDto> getAllCustomers(){
        List<Customer> fetchedCustomers = customerRepository.findAll();
        List<CustomerDto> customerDto = new ArrayList<>();

        if (!fetchedCustomers.isEmpty()) {

            for (Customer customer : fetchedCustomers) {
                CustomerDto dto = new CustomerDto();

                DtoConverters.customerDtoConverter(customer, dto);

                customerDto.add(dto);
            }
            return customerDto;
        } else {
            throw new RecordNotFoundException("We hebben geen klanten in onze database gevonden.");
        }
    }

    ///// For fetching customers by id from the database. /////
    public CustomerDto getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);


        if (customer.isPresent()) {
            CustomerDto dto = new CustomerDto();

            DtoConverters.customerDtoConverter(customer.get(), dto);

            return dto;
        } else {
            throw new RecordNotFoundException("We hebben geen klant met id: " + id + " in onze database.");
        }
    }

    ///// For fetching customers by surname and/or lastname from the database. /////
    public List<CustomerDto> getCustomerByName(String surName, String lastName) {
        List<Customer> fetchedCustomerList = customerRepository.findCustomersBySurNameIgnoreCaseOrLastNameIgnoreCase(surName, lastName);
        List<CustomerDto> customerList = new ArrayList<>();

        if (!fetchedCustomerList.isEmpty()) {
            for (Customer customer : fetchedCustomerList) {
                CustomerDto dto = new CustomerDto();

                DtoConverters.customerDtoConverter(customer, dto);
                customerList.add(dto);
            }
            return customerList;
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
            throw new RecordNotFoundException("We hebben geen klanten met naam: " + stringBuilder + " gevonden.");
        }
    }

    ///// For fetching customers by corporate or private from the database. /////
    public List<CustomerDto> getAllCorporateOrPrivate(Boolean corporate) {
        List<Customer> fetchedCustomerList = customerRepository.findCustomersByCorporate(corporate);
        List<CustomerDto> customerDtoList = new ArrayList<>();

        if (!fetchedCustomerList.isEmpty()) {

            for (Customer customer : fetchedCustomerList) {
                CustomerDto dto = new CustomerDto();

                DtoConverters.customerDtoConverter(customer, dto);
                customerDtoList.add(dto);
            }
            return customerDtoList;
        } else {
            if (corporate) {
                throw new RecordNotFoundException("We hebben zakelijke klanten gevonden.");
            } else {
                throw new RecordNotFoundException("We hebben geen private klanten gevonden.");
            }
        }
    }

    ///// For adding a customer in the database. /////
    public CustomerDto createCustomer(CustomerInputDto customer) {
        Optional<Customer> existingCustomer = customerRepository.findCustomersBySurNameIgnoreCaseAndLastNameIgnoreCase(customer.surName, customer.lastName);

        if(existingCustomer.isPresent()) {
            Customer customer1 = existingCustomer.get();
            throw new AlreadyExistsException("We hebben al een klant genaamd: " + customer1.getSurName() + " " + customer1.getLastName() + " met id: " + customer1.getId() + " in onze database.");

        } else {
            Customer newCustomer = new Customer();
            CustomerDto dto = new CustomerDto();

            DtoConverters.customerInputDtoConverter(newCustomer, customer);
            customerRepository.save(newCustomer);

            Optional<Customer> savedCustomer = customerRepository.findById(newCustomer.getId());
            DtoConverters.customerDtoConverter(savedCustomer.get(), dto);

            return dto;
        }
    }

    ///// For changing a customer in the database. /////
    public CustomerDto changeCustomer (Long id, CustomerDto customer) {
        Optional<Customer> fetchedCustomer = customerRepository.findById(id);
        CustomerDto returnCustomer = new CustomerDto();

        if (fetchedCustomer.isPresent()) {
            Customer customer1 = fetchedCustomer.get();

            if (customer.surName != null) {
                customer1.setSurName(customer.surName);
            }
            if (customer.lastName != null) {
                customer1.setLastName(customer.lastName);
            }
            if (customer.address != null) {
                customer1.setAddress(customer.address);
            }
            if (customer.phoneNumber != null) {
                customer1.setPhoneNumber(customer.phoneNumber);
            }
            if (customer.bankAccount != null) {
                customer1.setBankAccount(customer.bankAccount);
            }
            if (customer.corporate != null) {
                customer1.setCorporate(customer.corporate);
            }
            customerRepository.save(customer1);
            DtoConverters.customerDtoConverter(customer1, returnCustomer);

            return returnCustomer;

        } else {
            throw new RecordNotFoundException("We hebben geen klant met id: " + id + " gevonden.");
        }
    }

    ///// For deleting a customer from the database. /////
    public CustomerDto deleteCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isPresent()) {
            Customer customer1 = customer.get();
            CustomerDto dto = new CustomerDto();

            DtoConverters.customerDtoConverter(customer1, dto);

            customerRepository.deleteById(id);

            return dto;
        } else {
            throw new RecordNotFoundException("We hebben geen klant met id: " + id + " in onze database.");
        }
    }
}
