package com.exocommerce.order_service.controller;

import com.exocommerce.order_service.dto.OrderResponseDto;
import com.exocommerce.order_service.entity.Order;
import com.exocommerce.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // ✅ PLACE ORDER (EXISTING — UNCHANGED)
    @PostMapping
    public ResponseEntity<OrderResponseDto> placeOrder(
            @AuthenticationPrincipal Jwt jwt) {

        Long userId = jwt.getClaim("id");

        Order order = orderService.placeOrder(userId);

        OrderResponseDto response = OrderResponseDto.builder()
                .orderId(order.getId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .build();

        return ResponseEntity.ok(response);
    }

    // ✅ GET MY ORDERS (NEW, READ-ONLY)
    @GetMapping("/my")
    public ResponseEntity<List<OrderResponseDto>> getMyOrders(
            @AuthenticationPrincipal Jwt jwt) {

        Long userId = jwt.getClaim("id");

        List<OrderResponseDto> orders = orderService
                .getOrdersByUserId(userId)
                .stream()
                .map(order -> OrderResponseDto.builder()
                        .orderId(order.getId())
                        .totalAmount(order.getTotalAmount())
                        .status(order.getStatus())
                        .createdAt(order.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(orders);
    }
}
