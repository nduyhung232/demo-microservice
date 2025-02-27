package org.example.orderservice.service;

import org.example.orderservice.model.dto.OrderItemCreateDTO;
import org.example.orderservice.model.entity.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(List<OrderItemCreateDTO> items);
}
