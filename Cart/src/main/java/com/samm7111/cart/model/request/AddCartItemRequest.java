package com.samm7111.cart.model.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record AddCartItemRequest(
    @NotBlank String userId,
    @NotBlank String skuCode,
    @NotBlank String productName,
    @Min(1) int quantity,
    @NotNull @DecimalMin("0.0") BigDecimal price
) {
}