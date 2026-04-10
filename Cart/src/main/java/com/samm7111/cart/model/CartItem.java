package com.samm7111.cart.model;

import java.math.BigDecimal;

public record CartItem(
    String skuCode,
    String productName,
    int quantity,
    BigDecimal price
) {
}