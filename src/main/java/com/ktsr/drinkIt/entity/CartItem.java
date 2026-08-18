package com.ktsr.drinkIt.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cart_item", uniqueConstraints = {@UniqueConstraint(name = "uk_cart_product_variant",
        columnNames = {"cart_id", "product_variant_id"})})
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "cart_id", nullable = false, foreignKey = @ForeignKey(name = "fk_cart_item_cart"))
    private Cart cart;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "product_variant_id", nullable = false, foreignKey = @ForeignKey(name = "fk_cart_item_product_variant"))
    private ProductVariant productVariant;

    @NotNull
    @Min(value = 1, message = "Quantity should be at least 1")
    @Column(nullable = false)
    private Integer quantity;

    @NotNull
    @DecimalMin(value = "0.0")
    @Column(nullable = false)
    private Double price;

    @NotNull
    @DecimalMin(value = "0.0")
    @Column(nullable = false)
    private Double subtotal;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        calculateSubtotal();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
        calculateSubtotal();
    }

    private void calculateSubtotal() {
        if (price != null && quantity != null) {
            subtotal = price * quantity;
        }
    }
}