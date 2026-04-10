package com.samm7111.products.service;

import com.samm7111.products.model.Category;
import com.samm7111.products.model.Product;
import com.samm7111.products.model.request.CreateCategoryRequest;
import com.samm7111.products.model.request.CreateProductRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

@Service
public class InMemoryCatalogService implements CatalogService {

    private final AtomicLong categorySequence = new AtomicLong(1);
    private final AtomicLong productSequence = new AtomicLong(1);
    private final Map<Long, Category> categories = new ConcurrentHashMap<>();
    private final Map<String, Product> products = new ConcurrentHashMap<>();

    @Override
    public Category createCategory(CreateCategoryRequest request) {
        long id = categorySequence.getAndIncrement();
        Category category = new Category(id, request.name(), request.description());
        categories.put(id, category);
        return category;
    }

    @Override
    public List<Category> findCategories() {
        return new ArrayList<>(categories.values());
    }

    @Override
    public Product createProduct(CreateProductRequest request) {
        if (!categories.containsKey(request.categoryId())) {
            throw new IllegalArgumentException("Category not found");
        }
        long id = productSequence.getAndIncrement();
        String sku = "SKU-" + id;
        Product product = new Product(id, request.name(), sku, request.categoryId(), request.price(), request.description());
        products.put(sku, product);
        return product;
    }

    @Override
    public List<Product> findProducts() {
        return new ArrayList<>(products.values());
    }

    @Override
    public Product findProductBySku(String skuCode) {
        Product product = products.get(skuCode);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }
        return product;
    }
}