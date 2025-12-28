package com.exocommerce.cart_service.client;

import com.exocommerce.cart_service.dto.ProductCartDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "http://localhost:8081")
public interface ProductServiceClient {

    @GetMapping("/products/cart-details/{id}")
    ProductCartDto getCartDetailsById(@PathVariable("id") Long id);
}
