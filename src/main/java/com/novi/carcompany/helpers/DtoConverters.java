package com.novi.carcompany.helpers;

import com.novi.carcompany.dtos.businessEntities.*;
import com.novi.carcompany.models.businessEntities.*;

import java.util.ArrayList;
import java.util.List;


public class DtoConverters {

    public static void carInputDtoConverter(Car car, CarInputDto dto) {
        car.setLicensePlate(dto.licensePlate.toUpperCase());
        car.setBrand(dto.brand);
        car.setModel(dto.model);
        car.setVinNumber(dto.vinNumber.toUpperCase());
        car.setColor(dto.color);
        car.setEngine(dto.engine);
        car.setWinterTyres(dto.winterTyres);
    }

    public static void carDtoConverter(Car car, CarDto dto) {
        dto.licensePlate = car.getLicensePlate();
        dto.brand = car.getBrand();
        dto.model = car.getModel();
        dto.vinNumber = car.getVinNumber();
        dto.color = car.getColor();
        dto.engine = car.getEngine();
        dto.winterTyres = car.getWinterTyres();
        if (car.getCustomer() != null) {
            CustomerDto dto1 = new CustomerDto();
            DtoConverters.customerDtoConverter(car.getCustomer(), dto1);
            dto.customer = dto1;
        }
    }

    public static void partInputDtoConverter(Part part, PartInputDto dto) {
        part.setPartNumber(dto.partNumber.toUpperCase());
        part.setName(dto.name);
        part.setDescription(dto.description);
        part.setLocation(dto.location);
        part.setStock(dto.stock);
        part.setPurchasePrice(dto.purchasePrice);
        part.setSellingPrice(dto.sellingPrice);
    }

    public static void partDtoConverter(Part part, PartDto dto) {
        dto.partNumber = part.getPartNumber();
        dto.name = part.getName();
        dto.description = part.getDescription();
        dto.location = part.getLocation();
        dto.stock = part.getStock();
        dto.purchasePrice = part.getPurchasePrice();
        dto.sellingPrice = part.getSellingPrice();
        dto.picknote = part.getPicknote();
    }

    public static void employeeInputDtoConverter(Employee employee, EmployeeInputDto dto) {
        employee.setSurName(dto.surName);
        employee.setLastName(dto.lastName);
        employee.setAddress(dto.address);
        employee.setPhoneNumber(dto.phoneNumber);
        employee.setFunction(dto.function);
    }

    public static void employeeDtoConverter(Employee employee, EmployeeDto dto) {
        dto.surName = employee.getSurName();
        dto.lastName = employee.getLastName();
        dto.address = employee.getAddress();
        dto.phoneNumber = employee.getPhoneNumber();
        dto.id = employee.getId();
        dto.function = employee.getFunction();
    }

    public static void customerInputDtoConverter(Customer customer, CustomerInputDto dto) {
        customer.setSurName(dto.surName);
        customer.setLastName(dto.lastName);
        customer.setAddress(dto.address);
        customer.setPhoneNumber(dto.phoneNumber);
        customer.setBankAccount(dto.bankAccount);
        customer.setCorporate(dto.corporate);
    }

    public static void customerDtoConverter(Customer customer, CustomerDto dto) {
        dto.id = customer.getId();
        dto.surName = customer.getSurName();
        dto.lastName = customer.getLastName();
        dto.address = customer.getAddress();
        dto.phoneNumber = customer.getPhoneNumber();
        dto.bankAccount = customer.getBankAccount();
        dto.corporate = customer.getCorporate();
        if (customer.getCars() != null) {
            List<Car> cars = customer.getCars();
            List<String> licensePlates = new ArrayList<>();
            for (Car car : cars) {
                licensePlates.add(car.getLicensePlate());
            }
            dto.cars = licensePlates;
        }
        if (customer.getCustomer_user() != null) {
            dto.userName = customer.getCustomer_user().getUsername();
        }
    }

    public static void invoiceInputDtoConverter(Invoice invoice, InvoiceInputDto dto) {
        invoice.setTotalPrice(dto.totalPrice);
        invoice.setApproved(dto.approved);
        invoice.setPaid(dto.paid);
        invoice.setLaborHours(dto.laborHours);
    }

    public static void invoiceDtoConverter(Invoice invoice, InvoiceDto dto) {
        dto.invoiceNumber = invoice.getInvoiceNumber();
        dto.totalPrice = invoice.getTotalPrice();
        dto.approved = invoice.getApproved();
        dto.paid = invoice.getPaid();
        dto.laborHours = invoice.getLaborHours();
        if (invoice.getEmployee() != null) {
            EmployeeDto employeeDto1 = new EmployeeDto();
            DtoConverters.employeeDtoConverter(invoice.getEmployee(), employeeDto1);
            dto.employee = employeeDto1;
        }
        if (invoice.getCustomer() != null) {
            CustomerDto customerDto1 = new CustomerDto();
            DtoConverters.customerDtoConverter(invoice.getCustomer(), customerDto1);
            dto.customer = customerDto1;
        }
        if (invoice.getCar() != null) {
            CarDto carDto1 = new CarDto();
            DtoConverters.carDtoConverter(invoice.getCar(), carDto1);
            dto.car = carDto1;
        }
        if (invoice.getParts() != null) {
            List<PartDto> partDtoList = new ArrayList<>();
            for (Part part : invoice.getParts()) {
                PartDto dto1 = new PartDto();
                DtoConverters.partDtoConverter(part, dto1);
                partDtoList.add(dto1);
            }
            dto.parts = partDtoList;
        }
    }
}
