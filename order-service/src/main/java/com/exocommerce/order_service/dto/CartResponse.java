package com.exocommerce.order_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartResponse {

    private List<CartItemDto> items;
}
