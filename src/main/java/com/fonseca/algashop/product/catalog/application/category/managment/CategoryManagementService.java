package com.fonseca.algashop.product.catalog.application.category.managment;

import jakarta.validation.Valid;

import java.util.UUID;

public class CategoryManagementService {
    public UUID create(@Valid CategoryInput input) {
        return null;
    }

    public void update(UUID categoryId, CategoryInput input) {
    }

    public void disable(UUID categoryId) {

    }
}
