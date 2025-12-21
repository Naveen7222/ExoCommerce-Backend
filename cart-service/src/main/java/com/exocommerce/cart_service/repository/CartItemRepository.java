package com.exocommerce.cart_service.repository;

import com.exocommerce.cart_service.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // Find all items belonging to a specific cart
    List<CartItem> findByCartId(Long cartId);

    // Optional: find a specific item in a cart by productId
    CartItem findByCartIdAndProductId(Long cartId, Long productId);
}
