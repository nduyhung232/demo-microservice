package org.example.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.model.dto.OrderItemCreateDTO;
import org.example.orderservice.model.entity.Order;
import org.example.orderservice.model.entity.OrderItem;
import org.example.orderservice.model.mapper.OrderItemMapper;
import org.example.orderservice.repository.OrderItemRepo;
import org.example.orderservice.repository.OrderRepo;
import org.example.orderservice.service.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;
    private final OrderItemMapper orderItemMapper;

    @Override
    public Order createOrder(List<OrderItemCreateDTO> items) {
        // calculate total amount
        BigDecimal totalAmount = items.stream()
                .map(item -> item.price().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // create new order
        Order order = new Order();
        order.setUserId(1L);
        order.setTotalAmount(totalAmount);
        order.setIsActive(Boolean.TRUE);
        order = orderRepo.save(order);

        // save list of orderItem to the order
        for (OrderItemCreateDTO item : items) {
            OrderItem orderItem = orderItemMapper.toEntity(item);
            orderItem.setOrder(order);
            orderItemRepo.save(orderItem);
        }

        return order;
    }
}
