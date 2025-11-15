package com.exocommerce.cart_service.service;

import com.exocommerce.cart_service.entity.Cart;
import java.util.List;

public interface CartService {
    Cart createCart(Cart cart);
    Cart getCartById(Long id);
    List<Cart> getAllCarts();
    Cart updateCart(Long id, Cart updatedCart);
    void deleteCart(Long id);
}
