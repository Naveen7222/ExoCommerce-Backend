package com.exocommerce.cart_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Many items belong to one cart
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    /**
     * Product reference (owned by Product Service)
     */
    @Column(nullable = false)
    private Long productId;

    /**
     * How many units user wants
     */
    @Column(nullable = false)
    private int quantity;

    /**
     * Price snapshot at the time item was added
     */
    @Column(nullable = false)
    private double priceAtTime;

    /**
     * Derived value (not stored in DB)
     */
    @Transient
    public double getTotalPrice() {
        return priceAtTime * quantity;
    }
}
