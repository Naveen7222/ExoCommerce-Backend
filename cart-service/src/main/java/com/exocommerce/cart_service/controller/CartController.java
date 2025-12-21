package com.exocommerce.cart_service.controller;

import com.exocommerce.cart_service.dto.AddToCartRequest;
import com.exocommerce.cart_service.dto.CartItemResponse;
import com.exocommerce.cart_service.dto.UpdateCartItemRequest;
import com.exocommerce.cart_service.entity.Cart;
import com.exocommerce.cart_service.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // frontend URL
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<List<CartItemResponse>> addToCart(
            @RequestBody AddToCartRequest request) {

        cartService.addToCart(
                request.getUserId(),
                request.getProductId(),
                request.getQuantity()
        );

        return ResponseEntity.ok(
                cartService.getCartByUserId(request.getUserId())
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItemResponse>> getCart(@PathVariable Long userId) {
        List<CartItemResponse> items = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(items);
    }

//    DELETE http://localhost:8080/cart/1/items/101
    @DeleteMapping("/{userId}/items/{productId}")
    public ResponseEntity<Void> removeItem(
            @PathVariable Long userId,
            @PathVariable Long productId) {

        cartService.removeItem(userId, productId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{userId}/items/{productId}")
    public ResponseEntity<Void> updateQuantity(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @RequestBody UpdateCartItemRequest request) {

        cartService.updateQuantity(userId, productId, request.getQuantity());
        return ResponseEntity.noContent().build();
    }


}