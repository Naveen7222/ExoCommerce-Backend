package com.exocommerce.cart_service.service;

import com.exocommerce.cart_service.dto.CartItemResponse;

import java.util.List;

public interface CartService {
    void addToCart(Long userId, Long productId, int quantity);

    List<CartItemResponse> getMyCart(Long userId);

    void removeItem(Long userId, Long productId);

    void updateQuantity(Long userId, Long productId, int quantity);
}
