package com.samm7111.order.model;

import java.math.BigDecimal;

public record OrderItem(
    String skuCode,
    int quantity,
    BigDecimal unitPrice
) {
}