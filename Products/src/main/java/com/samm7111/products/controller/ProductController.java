package com.samm7111.products.controller;

import com.samm7111.products.model.Product;
import com.samm7111.products.model.request.CreateProductRequest;
import com.samm7111.products.service.CatalogService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final CatalogService catalogService;

    public ProductController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @PostMapping("/create")
    public ResponseEntity<Product> create(@Valid @RequestBody CreateProductRequest request) {
        return ResponseEntity.ok(catalogService.createProduct(request));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> all() {
        return ResponseEntity.ok(catalogService.findProducts());
    }

    @GetMapping("/sku/{skuCode}")
    public ResponseEntity<Product> bySku(@PathVariable String skuCode) {
        return ResponseEntity.ok(catalogService.findProductBySku(skuCode));
    }
}