package com.ktsr.drinkIt.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @Column(nullable = false, unique = true, length = 50)
    private String invoiceNumber;

    @Column(nullable = false, updatable = false)
    private LocalDateTime invoiceDate;

    @Column(length = 500)
    private String pdfUrl;

    @PrePersist
    public void onCreate() {
        if (invoiceDate == null) {
            invoiceDate = LocalDateTime.now();
        }
    }
}