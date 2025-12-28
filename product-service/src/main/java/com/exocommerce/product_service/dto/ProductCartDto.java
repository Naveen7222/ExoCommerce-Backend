package com.exocommerce.product_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCartDto {
    private String name;
    private String imageBase64;
    private Double price;
    private Integer stockQuantity;

}
