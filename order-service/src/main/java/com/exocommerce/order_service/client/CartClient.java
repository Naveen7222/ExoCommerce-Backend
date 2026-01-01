package com.exocommerce.order_service.client;

import com.exocommerce.order_service.dto.CartItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "cart-service")
public interface CartClient {

    @GetMapping("/carts")
    List<CartItemDto> getCartItems();

    @DeleteMapping("/carts/items/{productId}")
    void removeItem(@PathVariable Long productId);
}
