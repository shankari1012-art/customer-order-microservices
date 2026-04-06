package com.example.customerservice.mapper;

import com.example.customerservice.dto.OrderDTO;
import com.example.customerservice.entity.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public OrderDTO toDto(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getOrderDate(),
                order.getAmount()
        );
    }

    public List<OrderDTO> toDtoList(List<Order> orders) {
        return orders.stream().map(this::toDto).toList();
    }
}
