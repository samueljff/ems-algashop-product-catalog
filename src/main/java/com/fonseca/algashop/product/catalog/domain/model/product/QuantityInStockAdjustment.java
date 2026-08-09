package com.fonseca.algashop.product.catalog.domain.model.product;

import java.util.UUID;

public interface QuantityInStockAdjustment {
    Result increase(UUID productId, Integer quantity);
    Result decrease(UUID productId, Integer quantity);

    record Result(
        UUID productId,
        int previousQuantity,
        int newQuantity
    ){
        public boolean isOutOfStock() {
            return newQuantity == 0 && previousQuantity != 0;
        }

        public boolean inRestocked() {
            return newQuantity > 0 && previousQuantity == 0;
        }
    }
}
