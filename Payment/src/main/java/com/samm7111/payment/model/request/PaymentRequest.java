package com.samm7111.payment.model.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record PaymentRequest(
    @NotBlank String orderNumber,
    @NotNull @DecimalMin("0.0") BigDecimal amount,
    @NotBlank String method
) {
}