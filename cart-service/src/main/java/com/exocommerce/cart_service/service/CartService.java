package com.exocommerce.cart_service.service;

import com.exocommerce.cart_service.dto.CartItemResponse;
import com.exocommerce.cart_service.entity.Cart;

import java.util.List;

public interface CartService {

    Cart addToCart(Long userId, Long productId, int quantity);

    List<CartItemResponse> getCartByUserId(Long userId);
    void removeItem(Long userId, Long productId);
    void updateQuantity(Long userId, Long productId, int quantity);


}
