package com.exocommerce.cart_service.service;

import com.exocommerce.cart_service.dto.CartItemResponse;
import com.exocommerce.cart_service.dto.ProductDto;
import com.exocommerce.cart_service.entity.Cart;
import com.exocommerce.cart_service.entity.CartItem;
import com.exocommerce.cart_service.repository.CartRepository;
import com.exocommerce.cart_service.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.exocommerce.cart_service.client.ProductServiceClient;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductServiceClient productServiceClient;


    @Override
    public Cart addToCart(Long userId, Long productId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = Cart.builder()
                    .userId(userId)
                    .cartItems(new ArrayList<>())
                    .build();
            return cartRepository.save(newCart);
        });

        // Check if item exists in cart
        CartItem item = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);
        if (item != null) {
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            item = CartItem.builder()
                    .cart(cart)
                    .productId(productId)
                    .quantity(quantity)
                    .build();
            cart.getCartItems().add(item);
        }

        return cartRepository.save(cart);
    }
    @Override
    public List<CartItemResponse> getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> Cart.builder()
                        .userId(userId)
                        .cartItems(new ArrayList<>())
                        .build());
        return cart.getCartItems().stream().map(item -> {
            ProductDto product = productServiceClient.getCartDetailsById(item.getProductId());

            CartItemResponse resp = new CartItemResponse();
            resp.setProductId(item.getProductId());
            resp.setProductName(product.getName());
            resp.setImageUrl(product.getImageBase64());
            resp.setCurrentPrice(product.getPrice());
            resp.setQuantity(item.getQuantity());

            return resp;
        }).toList();
    }
    @Override
    public void removeItem(Long userId, Long productId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem item = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), productId);

        if (item == null) {
            throw new RuntimeException("Item not found in cart");
        }

        // IMPORTANT: remove from cartItems list
        cart.getCartItems().remove(item);

        cartRepository.save(cart);
    }
    @Override
    public void updateQuantity(Long userId, Long productId, int quantity) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem item = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), productId);

        if (item == null) {
            throw new RuntimeException("Item not found in cart");
        }

        if (quantity <= 0) {
            // remove item if quantity becomes zero
            cart.getCartItems().remove(item);
        } else {
            item.setQuantity(quantity);
        }

        cartRepository.save(cart);
    }


}
