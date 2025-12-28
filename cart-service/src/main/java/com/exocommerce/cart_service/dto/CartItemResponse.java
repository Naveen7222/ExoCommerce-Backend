package com.exocommerce.cart_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {

    private Long productId;

    // product details
    private String name;
    private Double price;
    private String imageBase64;

    // cart details
    private int quantity;

    // derived field
    private Double total;
}
