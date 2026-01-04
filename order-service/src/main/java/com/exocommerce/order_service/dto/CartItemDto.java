package com.exocommerce.order_service.dto;

import lombok.Data;

@Data
public class CartItemDto {

    private Long productId;
    private String name;
    private Double price;
    private Integer quantity;
}
