package com.fonseca.algashop.product.catalog.infrastructure.persistence.product;

import com.fonseca.algashop.product.catalog.application.product.query.ProductDetailOutput;
import com.fonseca.algashop.product.catalog.application.product.query.ProductQueryService;
import com.fonseca.algashop.product.catalog.application.utility.PageModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductQueryServiceImpl implements ProductQueryService {
    @Override
    public ProductDetailOutput findById(UUID productId) {
        return null;
    }

    @Override
    public PageModel<ProductDetailOutput> filter(Integer size, Integer number) {
        return null;
    }
}
