package com.exocommerce.cart_service.controller;

import com.exocommerce.cart_service.dto.AddToCartRequest;
import com.exocommerce.cart_service.dto.CartItemResponse;
import com.exocommerce.cart_service.dto.UpdateCartItemRequest;
import com.exocommerce.cart_service.security.JwtUtils;
import com.exocommerce.cart_service.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Void> addToCart(
            @Valid @RequestBody AddToCartRequest request
    ) {
        Long userId = JwtUtils.getUserId();
        cartService.addToCart(userId, request.getProductId(), request.getQuantity());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getMyCart() {
        Long userId = JwtUtils.getUserId();
        return ResponseEntity.ok(cartService.getMyCart(userId));
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeItem(
            @PathVariable Long productId
    ) {
        Long userId = JwtUtils.getUserId();
        cartService.removeItem(userId, productId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/items/{productId}")
    public ResponseEntity<Void> updateQuantity(
            @PathVariable Long productId,
            @Valid @RequestBody UpdateCartItemRequest request
    ) {
        Long userId = JwtUtils.getUserId();
        cartService.updateQuantity(userId, productId, request.getQuantity());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Cart Service is running");
    }
}
