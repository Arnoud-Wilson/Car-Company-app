package com.novi.carcompany.repositories;

import com.novi.carcompany.models.Invoice;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface InvoiceRepository extends JpaRepository<Invoice, Long>  {
    Boolean existsInvoiceByInvoiceNumber(Long invoiceNumber);
    Optional<Invoice> findInvoiceByInvoiceNumber(Long invoiceNumber);
    List<Invoice> findInvoiceByPaidFalse();
    List<Invoice> findInvoiceByCustomerId(Long customerId);
    @Transactional
    void deleteInvoiceByInvoiceNumber(Long invoiceNumber);
}
