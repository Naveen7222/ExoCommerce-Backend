package com.exocommerce.cart_service.dto;

import lombok.Data;

@Data
public class AddToCartRequest {
    private Long userId;
    private Long productId;
    private int quantity;
}
