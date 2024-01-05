package com.example.taskflow.service;

import com.example.taskflow.domain.Order;
import com.example.taskflow.domain.Task;
import com.example.taskflow.utils.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    List<Order> findAll();
    List<Order> findAllByStatus(String status);
    Order findById(Long id) throws ValidationException;
    Order update(Order order);
    void deleteById(Long id) throws ValidationException;

    Order createOrder(Task task);
}
