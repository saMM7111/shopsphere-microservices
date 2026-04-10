package com.samm7111.inventory.service;

import com.samm7111.inventory.model.InventoryItem;
import com.samm7111.inventory.model.request.InventoryRequest;
import java.util.List;

public interface InventoryService {
    InventoryItem upsert(InventoryRequest request);
    InventoryItem bySku(String skuCode);
    List<InventoryItem> all();
    InventoryItem deduct(String skuCode, int amount);
}