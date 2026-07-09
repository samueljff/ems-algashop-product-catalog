package com.fonseca.algashop.product.catalog.application.category.query;

import com.fonseca.algashop.product.catalog.application.utility.PageModel;

import java.util.UUID;

public interface CategoryQueryService {
    PageModel<CategoryDetailOutput> filter(Integer size, Integer number);
    CategoryDetailOutput findById(UUID categoryId);
}