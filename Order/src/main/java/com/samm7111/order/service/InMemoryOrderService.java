package com.samm7111.order.service;

import com.samm7111.order.model.Order;
import com.samm7111.order.model.OrderItem;
import com.samm7111.order.model.request.OrderItemRequest;
import com.samm7111.order.model.request.OrderRequest;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Service;

@Service
public class InMemoryOrderService implements OrderService {

    private final List<Order> orders = new CopyOnWriteArrayList<>();

    @Override
    public Order create(OrderRequest request) {
        List<OrderItem> items = request.items().stream()
            .map(this::toOrderItem)
            .toList();
        BigDecimal total = items.stream()
            .map(item -> item.unitPrice().multiply(BigDecimal.valueOf(item.quantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order(
            "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
            request.userId(),
            items,
            total,
            "PENDING",
            Instant.now());
        orders.add(order);
        return order;
    }

    @Override
    public Order byOrderNumber(String orderNumber) {
        return orders.stream()
            .filter(order -> order.orderNumber().equals(orderNumber))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

    @Override
    public List<Order> byUser(String userId) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (order.userId().equals(userId)) {
                result.add(order);
            }
        }
        return result;
    }

    private OrderItem toOrderItem(OrderItemRequest request) {
        return new OrderItem(request.skuCode(), request.quantity(), request.unitPrice());
    }
}