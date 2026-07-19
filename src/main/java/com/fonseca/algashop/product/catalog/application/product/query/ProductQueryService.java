package com.fonseca.algashop.product.catalog.application.product.query;

import com.fonseca.algashop.product.catalog.application.utility.PageModel;
import com.fonseca.algashop.product.catalog.infrastructure.persistence.product.ProductFilter;

import java.util.UUID;

public interface ProductQueryService {

    ProductDetailOutput findById(UUID productId);
    PageModel<ProductSummaryOutput> filter(ProductFilter filter);
}
