package org.example.orderservice.model.mapper;

import org.example.orderservice.model.dto.OrderItemCreateDTO;
import org.example.orderservice.model.entity.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItem toEntity(OrderItemCreateDTO orderItemCreateDTO);
}
