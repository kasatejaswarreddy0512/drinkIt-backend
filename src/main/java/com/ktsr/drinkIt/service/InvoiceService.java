package com.ktsr.drinkIt.service;

import com.ktsr.drinkIt.DTO.InvoiceDto;
import com.ktsr.drinkIt.entity.Invoice;

import java.util.List;

public interface InvoiceService {

    Invoice createInvoice(InvoiceDto invoiceDto);

    Invoice updateInvoice(Long id,InvoiceDto invoiceDto);

    Invoice getInvoiceById(Long id);

    Invoice getInvoiceOrderId(Long orderId);

    Invoice getInvoiceByInvoiceNumber(String invoiceNumber);

    List<Invoice> getAllInvoices();

    boolean existsById(Long id);

    void deleteInvoice(Long id);

}
