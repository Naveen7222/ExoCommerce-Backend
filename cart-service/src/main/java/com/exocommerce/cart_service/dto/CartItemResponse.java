package com.exocommerce.cart_service.dto;

import lombok.Data;

@Data
public class CartItemResponse {
    private Long productId;
    private String productName;
    private String imageUrl;   // from ProductDto.imageBase64
    private double currentPrice;
    private int quantity;
}
