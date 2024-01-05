package com.example.taskflow.service.impl;

import com.example.taskflow.domain.Order;
import com.example.taskflow.repository.OrderRepository;
import com.example.taskflow.service.OrderService;
import com.example.taskflow.utils.CustomError;
import com.example.taskflow.utils.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
    public Order findById(Long id) throws ValidationException {
        return orderRepository.findById(id).orElseThrow(
                () -> new ValidationException(new CustomError("id", "Order not found"))
        );
    }

    @Override
    public Order save(Order order) {
        // TODO: validation
        return orderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void deleteById(Long id) throws ValidationException {
        if (!isExist(id)) {
            throw new ValidationException(new CustomError("id", "Order not found"));
        }
        orderRepository.deleteById(id);
    }

    private boolean isExist(Long id) {
        return orderRepository.existsById(id);
    }
}
