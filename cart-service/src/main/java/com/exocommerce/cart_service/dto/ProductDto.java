package com.exocommerce.cart_service.dto;

import lombok.Data;

@Data
public class ProductDto {
    private String name;
    private String imageBase64;
    private Double price;
}
