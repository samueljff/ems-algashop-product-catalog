package com.fonseca.algashop.product.catalog.application.category.query;

import java.util.UUID;

public class CategoryOutputTestDataBuilder {

    private CategoryOutputTestDataBuilder() {
    }

    public static CategoryDetailOutput.CategoryDetailOutputBuilder aCategory() {
        return CategoryDetailOutput.builder()
                .id(UUID.randomUUID())
                .name("Electronics")
                .enabled(true);
    }

    public static CategoryDetailOutput.CategoryDetailOutputBuilder aCategoryAlt() {
        return CategoryDetailOutput.builder()
                .id(UUID.randomUUID())
                .name("Foods")
                .enabled(false);
    }
}