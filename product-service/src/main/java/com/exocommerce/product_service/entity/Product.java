package com.exocommerce.product_service.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Double price;

    private Integer stock;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imageData;

    // NEW: Add category relationship
    @ManyToOne
    @JoinColumn(name = "category_id") // FK column in products table
    private Category category;
}
