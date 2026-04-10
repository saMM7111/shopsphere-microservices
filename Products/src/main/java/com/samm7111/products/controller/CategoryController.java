package com.samm7111.products.controller;

import com.samm7111.products.model.Category;
import com.samm7111.products.model.request.CreateCategoryRequest;
import com.samm7111.products.service.CatalogService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CatalogService catalogService;

    public CategoryController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @PostMapping("/create")
    public ResponseEntity<Category> create(@Valid @RequestBody CreateCategoryRequest request) {
        return ResponseEntity.ok(catalogService.createCategory(request));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Category>> all() {
        return ResponseEntity.ok(catalogService.findCategories());
    }
}