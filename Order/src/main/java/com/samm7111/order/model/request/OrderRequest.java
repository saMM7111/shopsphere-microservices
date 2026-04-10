package com.samm7111.order.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record OrderRequest(
    @NotBlank String userId,
    @NotEmpty List<@Valid OrderItemRequest> items
) {
}