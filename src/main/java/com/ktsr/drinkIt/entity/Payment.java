package com.ktsr.drinkIt.entity;

import com.ktsr.drinkIt.enums.PaymentMethod;
import com.ktsr.drinkIt.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", unique = true, nullable = false,foreignKey = @ForeignKey(name = "fk_payment_order"))
    private Order order;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private String transactionId;

    private String paymentLinkId;

    @Column(nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private LocalDateTime paymentDate;

    @Lob
    private String gatewayResponse;

    @PrePersist
    public void onCreate() {
        if (paymentDate == null) {
            paymentDate = LocalDateTime.now();
        }
    }


}