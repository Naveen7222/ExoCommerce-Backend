package com.exocommerce.order_service.service;

import com.exocommerce.order_service.client.CartClient;
import com.exocommerce.order_service.dto.CartItemDto;
import com.exocommerce.order_service.entity.Order;
import com.exocommerce.order_service.entity.OrderItem;
import com.exocommerce.order_service.enums.OrderStatus;
import com.exocommerce.order_service.repository.OrderItemRepository;
import com.exocommerce.order_service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartClient cartClient;

    @Override
    @Transactional
    public Order placeOrder(Long userId) {

        // 1️⃣ Fetch cart items (FIXED)
        List<CartItemDto> cartItems = cartClient.getCartItems();

        if (cartItems == null || cartItems.isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        // 2️⃣ Create order
        Order order = Order.builder()
                .userId(userId)
                .status(OrderStatus.PLACED)
                .totalAmount(0.0)
                .build();

        order = orderRepository.save(order);

        // 3️⃣ Create order items
        double totalAmount = 0.0;

        for (CartItemDto item : cartItems) {

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

        // 4️⃣ Update order total
        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);

        // 5️⃣ Clear cart item-by-item (correct API usage)
        for (CartItemDto item : cartItems) {
            cartClient.removeItem(item.getProductId());
        }

        return savedOrder;
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
