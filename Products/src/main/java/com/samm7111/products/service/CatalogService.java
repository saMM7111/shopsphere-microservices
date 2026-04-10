package com.samm7111.products.service;

import com.samm7111.products.model.Category;
import com.samm7111.products.model.Product;
import com.samm7111.products.model.request.CreateCategoryRequest;
import com.samm7111.products.model.request.CreateProductRequest;
import java.util.List;

public interface CatalogService {
    Category createCategory(CreateCategoryRequest request);
    List<Category> findCategories();
    Product createProduct(CreateProductRequest request);
    List<Product> findProducts();
    Product findProductBySku(String skuCode);
}