package com.samm7111.inventory.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record InventoryRequest(
    @NotBlank String skuCode,
    @Min(0) int quantity,
    @Min(0) int safetyStock
) {
}