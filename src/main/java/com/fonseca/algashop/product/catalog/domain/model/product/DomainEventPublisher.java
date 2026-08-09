package com.fonseca.algashop.product.catalog.domain.model.product;

public interface DomainEventPublisher {
    void publish(Object event);
}