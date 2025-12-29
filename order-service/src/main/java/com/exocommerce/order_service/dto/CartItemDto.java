package com.exocommerce.order_service.dto;

import lombok.Data;

@Data
public class CartItemDto {

    private Long productId;
    private String productName;
    private Double price;
    private Integer quantity;
}
