package com.fonseca.algashop.product.catalog.infrastructure.persistence.product;

import com.fonseca.algashop.product.catalog.domain.model.product.Product;
import com.fonseca.algashop.product.catalog.domain.model.product.QuantityInStockAdjustment;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class QuantityInStockAdjustmentMongoDBImpl implements QuantityInStockAdjustment {

    private final MongoOperations mongoOperations;
    
    @Override
    public void increase(UUID productId, Integer quantity) {
        changeStockQuantity(productId, quantity);
    }

    @Override
    public void decrease(UUID productId, Integer quantity) {
        changeStockQuantity(productId, quantity * -1);
    }

    private void changeStockQuantity(UUID productId, Integer quantity) {
        Query query = Query.query(Criteria.where("id").is(productId));
        Update update = new Update()
            .inc("quantityInStock", quantity)
            .inc("version", 1)
            .set("updatedAt", LocalDateTime.now());

        UpdateResult updateResult = mongoOperations.update(Product.class)
            .matching(query)
            .apply(update)
            .first();
        if (updateResult.getModifiedCount() < 1) {
            throw new StockUpdateFailed("Product of id %s was not found");
        }
    }
}