package com.ktsr.drinkIt.repository;

import com.ktsr.drinkIt.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Invoice findByOrderId(Long orderId);

    Invoice findByInvoiceNumber(String invoiceNumber);

    boolean existsByInvoiceNumber(String invoiceNumber);

    boolean existsByOrderId(Long orderId);
}