package com.exocommerce.order_service.dto;

import com.exocommerce.order_service.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OrderResponseDto {

    private Long orderId;
    private Double totalAmount;
    private OrderStatus status;
    private LocalDateTime createdAt;
}
