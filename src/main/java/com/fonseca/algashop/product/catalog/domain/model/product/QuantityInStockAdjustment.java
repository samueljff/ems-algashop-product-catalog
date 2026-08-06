package com.fonseca.algashop.product.catalog.domain.model.product;

import java.util.UUID;

public interface QuantityInStockAdjustment {
    void increase(UUID productId, Integer quantity);
    void decrease(UUID productId, Integer quantity);
}
