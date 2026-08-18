package com.ktsr.drinkIt.service.impl;

import com.ktsr.drinkIt.DTO.InvoiceDto;
import com.ktsr.drinkIt.entity.Invoice;
import com.ktsr.drinkIt.entity.Order;
import com.ktsr.drinkIt.repository.InvoiceRepository;
import com.ktsr.drinkIt.repository.OrderRepository;
import com.ktsr.drinkIt.service.InvoiceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final OrderRepository orderRepository;

    @Override
    public Invoice createInvoice(InvoiceDto dto) {
        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Order not found"));

        Invoice existingInvoice = invoiceRepository.findByOrderId(order.getId());

        if (existingInvoice != null) {
            throw new IllegalArgumentException("Invoice already exists for this order");
        }

        Invoice invoice = Invoice.builder()
                .order(order)
                .invoiceNumber(generateInvoiceNumber(order))
                .invoiceDate(LocalDateTime.now())
                .pdfUrl(dto.getPdfUrl())
                .build();

        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice updateInvoice(Long id, InvoiceDto dto) {

        Invoice invoice = getInvoiceById(id);
        invoice.setPdfUrl(dto.getPdfUrl());
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .orElse(null);
    }

    @Override
    public Invoice getInvoiceOrderId(Long orderId) {
        return invoiceRepository.findByOrderId(orderId);
    }

    @Override
    public Invoice getInvoiceByInvoiceNumber(String invoiceNumber) {
        return invoiceRepository.findByInvoiceNumber(invoiceNumber);
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return invoiceRepository.existsById(id);
    }

    @Override
    public void deleteInvoice(Long id) {
        Invoice invoice = getInvoiceById(id);
        invoiceRepository.delete(invoice);
    }

    private String generateInvoiceNumber(Order order) {
        return "INV-"+order.getOrderNumber()+"-"+ LocalDate.now();
    }
}
