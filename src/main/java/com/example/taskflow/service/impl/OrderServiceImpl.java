package com.example.taskflow.service.impl;

import com.example.taskflow.domain.Order;
import com.example.taskflow.domain.Task;
import com.example.taskflow.domain.User;
import com.example.taskflow.domain.enums.OrderStatus;
import com.example.taskflow.repository.OrderRepository;
import com.example.taskflow.service.OrderService;
import com.example.taskflow.utils.CustomError;
import com.example.taskflow.utils.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;


    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findAllByStatus(String status) {
        return orderRepository.findAllByStatus(status);
    }

    @Override
    public Order createOrder(Task task) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        return orderRepository.save(Order.builder()
                .task(task)
                .creationDate(LocalDateTime.now())
                .status(OrderStatus.valueOf("PENDING"))
                .requestedBy(user)
                .assignedTo(task.getAssignedTo())
                .build());
    }

    @Override
    public Order findById(Long id) throws ValidationException {
        return orderRepository.findById(id).orElseThrow(
                () -> new ValidationException(new CustomError("id", "Order not found"))
        );
    }

    @Override
    public Order update(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void deleteById(Long id) throws ValidationException {
        if (!isExist(id))
            throw new ValidationException(new CustomError("id", "Order not found"));
        orderRepository.deleteById(id);
    }


    private boolean isExist(Long id) {
        return orderRepository.existsById(id);
    }
}
