package com.novi.carcompany.repositories;


import com.novi.carcompany.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long>  {
    Boolean existsInvoiceByInvoiceNumber(Long invoiceNumber);

    Optional<Invoice> findInvoiceByInvoiceNumber(Long invoiceNumber);

}
