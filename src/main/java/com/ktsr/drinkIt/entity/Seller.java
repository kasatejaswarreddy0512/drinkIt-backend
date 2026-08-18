package com.ktsr.drinkIt.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sellers" ,uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "phone"),
        @UniqueConstraint(columnNames = "license_number")
})
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true,length = 100)
    private String email;

    @Column(nullable = false, unique = true,length = 15)
    private String phone;

    @Column(name = "license_number",nullable = false, unique = true,length = 50)
    private String licenseNumber;

    @Column(name = "shop_name",nullable = false,length = 50)
    private String shopName;

    @Column(nullable = false,length = 500)
    private String address;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active= true;

    @Builder.Default
    @Column(nullable = false)
    private Boolean verified= false;

    @Column(name = "profile_image", length = 1000)
    private String profileImage;

    @Column(length = 100, name = "gst_number")
    private String gstNumber;

    @Column(nullable = false,length = 100, name = "pan_number")
    private String panNumber;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String state;

    @Column(length = 100, name = "pin_code")
    private String pincode;
    @Column(length = 100)
    private String country;

    @Column(nullable = false)
    @Builder.Default
    private Double rating = 0.0;


    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();

        if (active == null) {
            active = true;
        }
        if (verified == null) {
            verified = false;
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
