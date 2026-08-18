package com.ktsr.drinkIt.entity;

import com.ktsr.drinkIt.enums.DiscountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "coupons")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Coupon code is required")
    @Size(max = 50)
    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Size(max = 500)
    @Column(length = 500)
    private String description;

    @NotNull(message = "Discount type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscountType discountType;

    @NotNull(message = "Discount value is required")
    @DecimalMin(value = "0.0")
    @Column(nullable = false)
    private Double discountValue;

    @Builder.Default
    @DecimalMin(value = "0.0")
    @Column(nullable = false)
    private Double minimumAmount = 0.0;

    @NotNull(message = "Start date is required")
    @Column(nullable = false)
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @Column(nullable = false)
    private LocalDate endDate;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
