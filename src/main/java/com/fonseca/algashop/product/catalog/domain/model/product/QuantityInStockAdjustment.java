package com.fonseca.algashop.product.catalog.domain.model.product;

import java.util.UUID;

public interface QuantityInStockAdjustment {
    Result increase(UUID productId, Integer quantity);
    Result decrease(UUID productId, Integer quantity);

    record Result(
        UUID productId,
        int previousQuantity,
        int newQuantity
    ){}
}
