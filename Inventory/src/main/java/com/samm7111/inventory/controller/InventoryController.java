package com.samm7111.inventory.controller;

import com.samm7111.inventory.model.InventoryItem;
import com.samm7111.inventory.model.request.InventoryRequest;
import com.samm7111.inventory.service.InventoryService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<InventoryItem> create(@Valid @RequestBody InventoryRequest request) {
        return ResponseEntity.ok(inventoryService.upsert(request));
    }

    @GetMapping("/sku-code/{skuCode}")
    public ResponseEntity<InventoryItem> bySku(@PathVariable String skuCode) {
        return ResponseEntity.ok(inventoryService.bySku(skuCode));
    }

    @GetMapping("/all")
    public ResponseEntity<List<InventoryItem>> all() {
        return ResponseEntity.ok(inventoryService.all());
    }

    @PostMapping("/deduct/{skuCode}")
    public ResponseEntity<InventoryItem> deduct(@PathVariable String skuCode, @RequestParam int amount) {
        return ResponseEntity.ok(inventoryService.deduct(skuCode, amount));
    }
}