package com.samm7111.order.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record Order(
    String orderNumber,
    String userId,
    List<OrderItem> items,
    BigDecimal total,
    String status,
    Instant createdAt
) {
}