package com.ktsr.drinkIt.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product_images")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "product_id", nullable = false,foreignKey = @ForeignKey(name = "fk_product_image_product"))
    private Product product;

    @NotBlank(message = "Image URL is required")
    @Column(nullable = false,length = 500)
    private String imageUrl;

    @Builder.Default
    @Column(nullable = false)
    private Boolean primaryImage= false;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active= true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
    @PreUpdate
    public void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

}
