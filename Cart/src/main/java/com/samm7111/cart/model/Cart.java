package com.samm7111.cart.model;

import java.math.BigDecimal;
import java.util.List;

public record Cart(
    String userId,
    List<CartItem> items,
    BigDecimal total
) {
}