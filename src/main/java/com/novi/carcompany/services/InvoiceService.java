package com.novi.carcompany.services;

import com.novi.carcompany.dtos.CarDto;
import com.novi.carcompany.dtos.InvoiceDto;
import com.novi.carcompany.exceptions.RecordNotFoundException;
import com.novi.carcompany.helpers.DtoConverters;
import com.novi.carcompany.models.Car;
import com.novi.carcompany.models.Invoice;
import com.novi.carcompany.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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




}
