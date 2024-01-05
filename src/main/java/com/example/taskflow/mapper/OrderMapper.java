package com.example.taskflow.mapper;

import com.example.taskflow.domain.Order;
import com.example.taskflow.domain.enums.OrderStatus;
import com.example.taskflow.dto.OrderDto;

public class OrderMapper {
    private OrderMapper() {
    }

    public static OrderDto toDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .status(order.getStatus().name())
                .creationDate(order.getCreationDate())
                .taskId(order.getTask().getId())
                .requestedBy(order.getRequestedBy().getUsername())
                .assignedBy(order.getAssignedBy() != null ? order.getAssignedBy().getUsername() : null)
                .build();
    }

    public static Order toEntity(OrderDto orderDto) {
        return Order.builder()
                .id(orderDto.getId())
                .status(OrderStatus.valueOf(orderDto.getStatus()))
                .creationDate(orderDto.getCreationDate())
                .build();
    }
}
