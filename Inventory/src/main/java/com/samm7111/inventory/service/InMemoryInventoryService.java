package com.samm7111.inventory.service;

import com.samm7111.inventory.model.InventoryItem;
import com.samm7111.inventory.model.request.InventoryRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class InMemoryInventoryService implements InventoryService {

    private final Map<String, InventoryItem> items = new ConcurrentHashMap<>();

    @Override
    public InventoryItem upsert(InventoryRequest request) {
        InventoryItem item = new InventoryItem(request.skuCode(), request.quantity(), request.safetyStock());
        items.put(request.skuCode(), item);
        return item;
    }

    @Override
    public InventoryItem bySku(String skuCode) {
        InventoryItem item = items.get(skuCode);
        if (item == null) {
            throw new IllegalArgumentException("SKU not found");
        }
        return item;
    }

    @Override
    public List<InventoryItem> all() {
        return new ArrayList<>(items.values());
    }

    @Override
    public InventoryItem deduct(String skuCode, int amount) {
        InventoryItem existing = bySku(skuCode);
        if (existing.quantity() - amount < existing.safetyStock()) {
            throw new IllegalStateException("Deduction violates safety stock");
        }
        InventoryItem updated = new InventoryItem(existing.skuCode(), existing.quantity() - amount, existing.safetyStock());
        items.put(skuCode, updated);
        return updated;
    }
}