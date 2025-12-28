package com.exocommerce.cart_service.service;

import com.exocommerce.cart_service.client.ProductServiceClient;
import com.exocommerce.cart_service.dto.CartItemResponse;
import com.exocommerce.cart_service.dto.ProductCartDto;
import com.exocommerce.cart_service.entity.Cart;
import com.exocommerce.cart_service.entity.CartItem;
import com.exocommerce.cart_service.exception.CartItemNotFoundException;
import com.exocommerce.cart_service.exception.CartNotFoundException;
import com.exocommerce.cart_service.exception.ProductNotAvailableException;
import com.exocommerce.cart_service.repository.CartItemRepository;
import com.exocommerce.cart_service.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductServiceClient productServiceClient;

    @Override
    public void addToCart(Long userId, Long productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        ProductCartDto product;
        try {
            product = productServiceClient.getCartDetailsById(productId);
        } catch (Exception ex) {
            throw new ProductNotAvailableException("Unable to fetch product details");
        }

        Integer stock = product.getStock();

        if (stock == null) {
            throw new ProductNotAvailableException("Product stock not available");
        }

        if (stock < quantity) {
            throw new ProductNotAvailableException("Product is not available in the required quantity");
        }



        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() ->
                        cartRepository.save(
                                Cart.builder().userId(userId).build()
                        )
                );

        CartItem item = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), productId)
                .orElseGet(() ->
                        CartItem.builder()
                                .cart(cart)
                                .productId(productId)
                                .quantity(0)
                                .build()
                );

        item.setQuantity(item.getQuantity() + quantity);
        cartItemRepository.save(item);
    }

    @Override
    public List<CartItemResponse> getMyCart(Long userId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));

        return cartItemRepository.findByCartId(cart.getId())
                .stream()
                .map(item -> {

                    ProductCartDto product;
                    try {
                        product = productServiceClient.getCartDetailsById(item.getProductId());
                    } catch (Exception ex) {
                        throw new ProductNotAvailableException(
                                "Unable to fetch product details for productId: " + item.getProductId()
                        );
                    }

                    double total = product.getPrice() * item.getQuantity();

                    return CartItemResponse.builder()
                            .productId(item.getProductId())
                            .name(product.getName())
                            .price(product.getPrice())
                            .imageBase64(product.getImageBase64())
                            .quantity(item.getQuantity())
                            .total(total)
                            .build();
                })
                .toList();
    }


    @Override
    public void removeItem(Long userId, Long productId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));

        CartItem item = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() ->
                        new CartItemNotFoundException("Item not found in cart")
                );

        cartItemRepository.delete(item);
    }


    @Override
    public void updateQuantity(Long userId, Long productId, int quantity) {
        ProductCartDto product;
        try {
            product = productServiceClient.getCartDetailsById(productId);
        } catch (Exception ex) {
            throw new ProductNotAvailableException("Unable to fetch product details");
        }

        Integer stock = product.getStock();

        if (stock == null) {
            throw new ProductNotAvailableException("Product stock not available");
        }

        if (stock < quantity) {
            throw new ProductNotAvailableException("Product is not available in the required quantity");
        }


        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));

        if (quantity <= 0) {

            CartItem item = cartItemRepository
                    .findByCartIdAndProductId(cart.getId(), productId)
                    .orElseThrow(() ->
                            new CartItemNotFoundException("Item not found in cart")
                    );

            cartItemRepository.delete(item);
            return;
        }


        CartItem item = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new CartItemNotFoundException("Item not found"));

        item.setQuantity(quantity);
        cartItemRepository.save(item);
    }
}
