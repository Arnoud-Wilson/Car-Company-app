package com.novi.carcompany.helpers;

import com.novi.carcompany.dtos.*;
import com.novi.carcompany.models.*;

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
    }

    public static void invoiceInputDtoConverter(Invoice invoice, InvoiceInputDto dto) {
        invoice.setTotaalPrice(dto.totalPrice);
        invoice.setApproved(dto.approved);
        invoice.setPaid(dto.paid);
        invoice.setLaborHours(dto.laborHours);
    }

    public static void invoiceDtoConverter(Invoice invoice, InvoiceDto dto) {
        dto.invoiceNumber = invoice.getInvoiceNumber();
        dto.totalPrice = invoice.getTotaalPrice();
        dto.approved = invoice.getApproved();
        dto.paid = invoice.getPaid();
        dto.laborHours = invoice.getLaborHours();
    }
}
