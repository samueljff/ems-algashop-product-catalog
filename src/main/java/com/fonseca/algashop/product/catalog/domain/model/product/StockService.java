package com.fonseca.algashop.product.catalog.domain.model.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StockService {

    private final QuantityInStockAdjustment quantityInStockAdjustment;
    private final DomainEventPublisher domainEventPublisher;

    public void restock(Product product, int quantity) {
        Objects.requireNonNull(product);

        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity must be greater than or equal to 1");
        }

        var result = quantityInStockAdjustment.increase(product.getId(), quantity);

        if (result.inRestocked()){
            domainEventPublisher.publish(ProductRestockedEvent.builder()
                .productId(product.getId())
                .build());
        }
    }

    public void withdraw(Product product, int quantity) {
        Objects.requireNonNull(product);
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity must be greater than or equal to 1");
        }
        var result = quantityInStockAdjustment.decrease(product.getId(), quantity);
        if (result.isOutOfStock()){
            domainEventPublisher.publish(ProductSoldOutEvent.builder()
                .productId(product.getId())
                .build());
        }
    }
}
