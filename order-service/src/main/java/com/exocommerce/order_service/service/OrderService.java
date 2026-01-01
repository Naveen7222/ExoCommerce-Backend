package com.exocommerce.order_service.service;

import com.exocommerce.order_service.entity.Order;

import java.util.List;

public interface OrderService {

    Order placeOrder(Long userId);

    List<Order> getOrdersByUserId(Long userId);
}
