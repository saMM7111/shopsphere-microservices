package com.samm7111.inventory.model;

public record InventoryItem(
    String skuCode,
    int quantity,
    int safetyStock
) {
}