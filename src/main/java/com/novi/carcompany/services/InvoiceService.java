package com.novi.carcompany.services;

import com.novi.carcompany.dtos.*;
import com.novi.carcompany.exceptions.AlreadyExistsException;
import com.novi.carcompany.exceptions.IllegalChangeException;
import com.novi.carcompany.exceptions.RecordNotFoundException;
import com.novi.carcompany.helpers.DtoConverters;
import com.novi.carcompany.models.*;
import com.novi.carcompany.repositories.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;
    private final PartRepository partRepository;


    public InvoiceService(InvoiceRepository invoiceRepository, EmployeeRepository employeeRepository, CustomerRepository customerRepository, CarRepository carRepository, PartRepository partRepository) {
        this.invoiceRepository = invoiceRepository;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
        this.partRepository = partRepository;
    }


    ///// For fetching all invoices. /////
    public List<InvoiceDto> getAllInvoices() {
        List<Invoice> fetchedInvoices = invoiceRepository.findAll();
        List<InvoiceDto> invoiceDto = new ArrayList<>();

        if (!fetchedInvoices.isEmpty()) {

            for (Invoice invoice : fetchedInvoices) {
                InvoiceDto dto = new InvoiceDto();

                DtoConverters.invoiceDtoConverter(invoice, dto);

                invoiceDto.add(dto);
            }
            return invoiceDto;
        } else {
            throw new RecordNotFoundException("We hebben geen facturen in onze database gevonden.");
        }
    }

    ///// For fetching invoice by invoice number. /////
    public InvoiceDto getInvoiceByInvoiceNumber(Long invoiceNumber) {

        if (invoiceRepository.existsInvoiceByInvoiceNumber(invoiceNumber)) {
            InvoiceDto dto = new InvoiceDto();
            Invoice fetchedInvoice = invoiceRepository.findInvoiceByInvoiceNumber(invoiceNumber).get();

            DtoConverters.invoiceDtoConverter(fetchedInvoice, dto);

            return dto;
        } else {
            throw new RecordNotFoundException("We hebben geen factuur met nummer: " + invoiceNumber + " in onze database.");
        }
    }

    ///// For fetching unpaid invoices. /////
    public List<InvoiceDto> getAllUnpaidInvoices() {
        List<Invoice> unpaidInvoices = invoiceRepository.findInvoiceByPaidFalse();
        List<InvoiceDto> invoiceDtos = new ArrayList<>();

        if (!unpaidInvoices.isEmpty()) {
            for (Invoice invoice : unpaidInvoices) {
                InvoiceDto dto = new InvoiceDto();

                DtoConverters.invoiceDtoConverter(invoice, dto);
                invoiceDtos.add(dto);
            }
            return invoiceDtos;
        } else {
            throw new RecordNotFoundException("We hebben geen onbetaalde facturen.");
        }
    }


    ///// For fetching invoices by customer. /////
    public List<InvoiceDto> getCustomerInvoices(Long customerId) {
        List<Invoice> invoices = invoiceRepository.findInvoiceByCustomerId(customerId);
        List<InvoiceDto> invoiceDtos = new ArrayList<>();

        if (!invoices.isEmpty()) {
            for (Invoice invoice : invoices) {
                InvoiceDto dto = new InvoiceDto();

                DtoConverters.invoiceDtoConverter(invoice, dto);
                invoiceDtos.add(dto);
            }
            return invoiceDtos;
        } else {
            throw new RecordNotFoundException("We hebben geen facturen voor klant met id: " + customerId + ".");
        }
    }

    ///// For adding new invoice. /////
    public InvoiceDto createInvoice(InvoiceInputDto invoice) {
        Invoice newInvoice = new Invoice();
        InvoiceDto returnInvoice = new InvoiceDto();

        DtoConverters.invoiceInputDtoConverter(newInvoice, invoice);

        if (invoiceRepository.existsInvoiceByInvoiceNumber(newInvoice.getInvoiceNumber())) {
            throw new AlreadyExistsException("We hebben al een factuur met nummer: " + newInvoice.getInvoiceNumber() + " in onze database.");
        } else {
            invoiceRepository.save(newInvoice);

            Invoice fetchedInvoice = invoiceRepository.findInvoiceByInvoiceNumber(newInvoice.getInvoiceNumber()).get();
            DtoConverters.invoiceDtoConverter(fetchedInvoice, returnInvoice);

            return returnInvoice;
        }
    }

    ///// For changing invoice. /////
    public InvoiceDto changeInvoice(Long invoiceNumber, InvoiceDto invoice) {
        Optional<Invoice> fetchedInvoice = invoiceRepository.findInvoiceByInvoiceNumber(invoiceNumber);
        InvoiceDto returnInvoice = new InvoiceDto();

        if (fetchedInvoice.isPresent()) {
            Invoice invoice1 = fetchedInvoice.get();
            if (invoiceNumber.equals(invoice1.getInvoiceNumber()) || invoice.invoiceNumber == null) {
                if (invoice.totalPrice != null) {
                    invoice1.setTotalPrice(invoice.totalPrice);
                }
                if (invoice.approved != null) {
                    invoice1.setApproved(invoice.approved);
                }
                if (invoice.paid != null) {
                    invoice1.setPaid(invoice.paid);
                }
                if (invoice.laborHours != null) {
                    invoice1.setLaborHours(invoice.laborHours);
                }
                invoiceRepository.save(invoice1);

                Invoice changedInvoice = invoiceRepository.findInvoiceByInvoiceNumber(invoiceNumber).get();
                DtoConverters.invoiceDtoConverter(changedInvoice, returnInvoice);

                return returnInvoice;

            } else {
                throw new IllegalChangeException("Het is niet mogelijk om het factuur nummer aan te passen.");
            }
        } else {
            throw new RecordNotFoundException("We hebben geen factuur met nummer: " + invoiceNumber + " in onze database.");
        }
    }

    ///// For assigning employee to invoice. /////
    public InvoiceDto assignEmployeeToInvoice(Long invoiceNumber, NumberInputDto employeeId) {
        Optional<Invoice> fetchedInvoice =  invoiceRepository.findInvoiceByInvoiceNumber(invoiceNumber);
        Optional<Employee> fetchedEmployee = employeeRepository.findById(employeeId.id);

        if (fetchedInvoice.isPresent()) {
            if (fetchedEmployee.isPresent()) {
                Invoice invoice = fetchedInvoice.get();
                Employee employee = fetchedEmployee.get();
                InvoiceDto dto = new InvoiceDto();

                invoice.setEmployee(employee);
                invoiceRepository.save(invoice);

                Invoice modifiedInvoice = invoiceRepository.findInvoiceByInvoiceNumber(invoiceNumber).get();
                DtoConverters.invoiceDtoConverter(modifiedInvoice, dto);

                return dto;
            } else {
                throw new RecordNotFoundException("We hebben geen werknemer met id: " + employeeId.id + ".");
            }
        } else {
            throw new RecordNotFoundException("We hebben geen factuur met nummer: " + invoiceNumber + ".");
        }
    }

    ///// For assigning customer to invoice. /////
    public InvoiceDto assignCustomerToInvoice(Long invoiceNumber, NumberInputDto customerId) {
        Optional<Invoice> fetchedInvoice =  invoiceRepository.findInvoiceByInvoiceNumber(invoiceNumber);
        Optional<Customer> fetchedCustomer = customerRepository.findById(customerId.id);

        if (fetchedInvoice.isPresent()) {
            Invoice invoice = fetchedInvoice.get();
            if (fetchedCustomer.isPresent()) {
                Customer customer = fetchedCustomer.get();
                InvoiceDto dto = new InvoiceDto();

                invoice.setCustomer(customer);
                invoiceRepository.save(invoice);

                Invoice modifiedInvoice = invoiceRepository.findInvoiceByInvoiceNumber(invoiceNumber).get();
                DtoConverters.invoiceDtoConverter(modifiedInvoice, dto);

                return dto;
            } else {
                throw new RecordNotFoundException("We hebben geen klant met id: " + customerId.id + ".");
            }
        } else {
            throw new RecordNotFoundException("We hebben geen factuur met nummer: " + invoiceNumber + ".");
        }
    }

    ///// For assigning car to invoice. /////
    public InvoiceDto assignCarToInvoice(Long invoiceNumber, StringInputDto licensePlate) {
        Optional<Invoice> fetchedInvoice =  invoiceRepository.findInvoiceByInvoiceNumber(invoiceNumber);
        Optional<Car> fetchedCar = carRepository.findByLicensePlateIgnoreCase(licensePlate.id);

        if (fetchedInvoice.isPresent()) {
            if (fetchedCar.isPresent()) {
                Invoice invoice = fetchedInvoice.get();
                Car car = fetchedCar.get();
                InvoiceDto dto = new InvoiceDto();

                invoice.setCar(car);
                invoiceRepository.save(invoice);

                Invoice modifiedInvoice = invoiceRepository.findInvoiceByInvoiceNumber(invoiceNumber).get();
                DtoConverters.invoiceDtoConverter(modifiedInvoice, dto);

                return dto;
            } else {
                throw new RecordNotFoundException("We hebben geen auto met kenteken: " + licensePlate.id + ".");
            }
        } else {
            throw new RecordNotFoundException("We hebben geen factuur met nummer: " + invoiceNumber + ".");
        }
    }

    ///// For assigning parts to invoice. /////
    public InvoiceDto assignPartToInvoice(Long invoiceNumber, StringInputDto partNumber) {
        Optional<Invoice> fetchedInvoice =  invoiceRepository.findInvoiceByInvoiceNumber(invoiceNumber);
        Optional<Part> fetchedPart = partRepository.findByPartNumberIgnoreCase(partNumber.id);

        if (fetchedInvoice.isPresent()) {
            Invoice invoice = fetchedInvoice.get();
            if (fetchedPart.isPresent()) {
                Part part = fetchedPart.get();
                List<Part> partsInInvoiceList = invoice.getParts();
                InvoiceDto dto = new InvoiceDto();

                partsInInvoiceList.add(part);
                invoice.setParts(partsInInvoiceList);
                invoiceRepository.save(invoice);

                Invoice modifiedInvoice = invoiceRepository.findInvoiceByInvoiceNumber(invoiceNumber).get();
                DtoConverters.invoiceDtoConverter(modifiedInvoice, dto);

                return dto;
            } else {
                throw new RecordNotFoundException("We hebben geen onderdeel met nummer: " + partNumber.id + ".");
            }
        } else {
            throw new RecordNotFoundException("We hebben geen factuur met nummer: " + invoiceNumber + ".");
        }
    }

    ///// For deleting invoice by invoice number. /////
    public InvoiceDto deleteInvoice(Long invoiceNumber) {
        Optional<Invoice> invoice = invoiceRepository.findInvoiceByInvoiceNumber(invoiceNumber);

        if (invoice.isPresent()) {
            Invoice invoice1 = invoice.get();
            InvoiceDto dto = new InvoiceDto();

            DtoConverters.invoiceDtoConverter(invoice1, dto);

            invoiceRepository.deleteInvoiceByInvoiceNumber(invoiceNumber);

            return dto;
        } else {
            throw new RecordNotFoundException("We hebben geen factuur met nummer: " + invoiceNumber + " in onze database.");
        }
    }
}
