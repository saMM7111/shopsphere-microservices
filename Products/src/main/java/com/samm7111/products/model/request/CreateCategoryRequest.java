package com.samm7111.products.model.request;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequest(
    @NotBlank String name,
    @NotBlank String description
) {
}