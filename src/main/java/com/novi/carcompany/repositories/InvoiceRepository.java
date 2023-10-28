package com.novi.carcompany.repositories;

import com.novi.carcompany.models.Employee;
import com.novi.carcompany.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long>  {
}
