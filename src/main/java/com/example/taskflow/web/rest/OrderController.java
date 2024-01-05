package com.example.taskflow.web.rest;

import com.example.taskflow.domain.Order;
import com.example.taskflow.domain.Task;
import com.example.taskflow.domain.User;
import com.example.taskflow.domain.enums.OrderStatus;
import com.example.taskflow.dto.OrderDto;
import com.example.taskflow.service.OrderService;
import com.example.taskflow.utils.Response;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<Response<List<Order>>> findAll(@RequestParam(required = false) String status) {
        Response<List<Order>> response = new Response<>();
        response.setResult(status==null?orderService.findAll():orderService.findAllByStatus(status));
        if (response.getResult().isEmpty())
            response.setMessage("No orders found");
        return ResponseEntity.ok(response);
    }


}
