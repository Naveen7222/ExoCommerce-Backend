package com.exocommerce.cart_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductCartDto {
    private String name;
    private String imageBase64;
    private Double price;
    @JsonProperty("stockQuantity") // MUST match product-service
    private Integer stock;
}
