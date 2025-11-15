package com.exocommerce.cart_service.service;

import com.exocommerce.cart_service.entity.Cart;
import com.exocommerce.cart_service.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    @Override
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    @Override
    public Cart updateCart(Long id, Cart updatedCart) {
        Cart existing = cartRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setUserId(updatedCart.getUserId());
            existing.setProductIds(updatedCart.getProductIds());
            existing.setTotalPrice(updatedCart.getTotalPrice());
            return cartRepository.save(existing);
        }
        return null;
    }

    @Override
    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }
}
