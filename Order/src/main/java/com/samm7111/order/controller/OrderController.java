package com.samm7111.order.controller;

import com.samm7111.order.model.Order;
import com.samm7111.order.model.request.OrderRequest;
import com.samm7111.order.service.OrderService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<Order> create(@Valid @RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.create(request));
    }

    @GetMapping("/{orderNumber}")
    public ResponseEntity<Order> byOrderNumber(@PathVariable String orderNumber) {
        return ResponseEntity.ok(orderService.byOrderNumber(orderNumber));
    }

    @GetMapping("/my-orders")
    public ResponseEntity<List<Order>> myOrders(@RequestParam String userId) {
        return ResponseEntity.ok(orderService.byUser(userId));
    }
}