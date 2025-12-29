package com.exocommerce.order_service.service.impl;

import com.exocommerce.order_service.client.CartClient;
import com.exocommerce.order_service.dto.CartItemDto;
import com.exocommerce.order_service.dto.CartResponse;
import com.exocommerce.order_service.entity.Order;
import com.exocommerce.order_service.entity.OrderItem;
import com.exocommerce.order_service.enums.OrderStatus;
import com.exocommerce.order_service.repository.OrderItemRepository;
import com.exocommerce.order_service.repository.OrderRepository;
import com.exocommerce.order_service.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartClient cartClient;

    @Override
    @Transactional
    public Order placeOrder(Long userId) {

        CartResponse cartResponse = cartClient.getCart();

        if (cartResponse == null
                || cartResponse.getItems() == null
                || cartResponse.getItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        Order order = Order.builder()
                .userId(userId)
                .status(OrderStatus.PLACED)
                .totalAmount(0.0)
                .build();

        order = orderRepository.save(order);

        double totalAmount = 0.0;

        for (CartItemDto item : cartResponse.getItems()) {

            double subtotal = item.getPrice() * item.getQuantity();

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .productId(item.getProductId())
                    .productName(item.getProductName())
                    .price(item.getPrice())
                    .quantity(item.getQuantity())
                    .subtotal(subtotal)
                    .build();

            orderItemRepository.save(orderItem);
            totalAmount += subtotal;
        }

        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);

        // ✅ Clear cart AFTER successful order creation
        cartClient.clearCart();

        return savedOrder;
    }
}
