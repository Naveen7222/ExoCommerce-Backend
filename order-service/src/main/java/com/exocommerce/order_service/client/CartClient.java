package com.exocommerce.order_service.client;

import com.exocommerce.order_service.dto.CartResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "cart-service")
public interface CartClient {

    @GetMapping("/carts")
    CartResponse getCart();

    @DeleteMapping("/carts")
    void clearCart();
}
