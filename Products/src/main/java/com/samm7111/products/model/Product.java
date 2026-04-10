package com.samm7111.products.model;

import java.math.BigDecimal;

public record Product(
    Long id,
    String name,
    String skuCode,
    Long categoryId,
    BigDecimal price,
    String description
) {
}