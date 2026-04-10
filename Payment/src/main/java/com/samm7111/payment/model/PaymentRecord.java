package com.samm7111.payment.model;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentRecord(
    String reference,
    String orderNumber,
    BigDecimal amount,
    String method,
    String status,
    Instant createdAt
) {
}