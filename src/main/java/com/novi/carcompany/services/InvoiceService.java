package com.novi.carcompany.services;

import com.novi.carcompany.dtos.InvoiceDto;
import com.novi.carcompany.dtos.InvoiceInputDto;
import com.novi.carcompany.exceptions.AlreadyExistsException;
import com.novi.carcompany.exceptions.IllegalChangeException;
import com.novi.carcompany.exceptions.RecordNotFoundException;
import com.novi.carcompany.helpers.DtoConverters;
import com.novi.carcompany.models.Invoice;
import com.novi.carcompany.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;


    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    ///// For fetching all invoices in database. /////
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

    ///// For fetching invoice by invoice number from database. /////
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

    ///// For fetching unpaid invoices from database. /////
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

    ///// For creating new invoice in database. /////
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

    ///// For changing invoice in database. /////
    public InvoiceDto changeInvoice(Long invoiceNumber, InvoiceDto invoice) {
        Optional<Invoice> fetchedInvoice = invoiceRepository.findInvoiceByInvoiceNumber(invoiceNumber);
        InvoiceDto returnInvoice = new InvoiceDto();

        if (fetchedInvoice.isPresent()) {
            Invoice invoice1 = fetchedInvoice.get();
            if (invoiceNumber.equals(invoice1.getInvoiceNumber()) || invoice.invoiceNumber == null) {
                if (invoice.totalPrice != null) {
                    invoice1.setTotaalPrice(invoice.totalPrice);
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

    ///// For deleting invoice from database. /////
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
