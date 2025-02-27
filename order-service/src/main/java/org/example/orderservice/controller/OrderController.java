package org.example.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.model.dto.OrderItemCreateDTO;
import org.example.orderservice.model.entity.Order;
import org.example.orderservice.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping()
    public Order createOrder(@AuthenticationPrincipal Jwt jwt,
                             @RequestBody(required = false) OrderItemCreateDTO orderItemCreateDTO) {
        System.out.println("User ID: " + jwt.getSubject());
        return null;
    }
}
