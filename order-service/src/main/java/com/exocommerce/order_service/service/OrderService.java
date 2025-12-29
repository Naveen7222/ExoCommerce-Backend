package com.exocommerce.order_service.service;

import com.exocommerce.order_service.entity.Order;

public interface OrderService {

    Order placeOrder(Long userId);
}
