package com.fonseca.algashop.product.catalog.infrastructure.persistence.product;

import com.fonseca.algashop.product.catalog.domain.model.product.Product;
import com.fonseca.algashop.product.catalog.domain.model.product.ProductNotFoundException;
import com.fonseca.algashop.product.catalog.domain.model.product.QuantityInStockAdjustment;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class QuantityInStockAdjustmentMongoDBImpl implements QuantityInStockAdjustment {

    private final MongoOperations mongoOperations;
    
    @Override
    public Result increase(UUID productId, Integer quantity) {
        Query query = Query.query(Criteria.where("id").is(productId));
        return changeStockQuantity(productId, quantity, query);
    }

    @Override
    public Result decrease(UUID productId, Integer quantity) {
        Query query = Query.query(Criteria.where("id").is(productId)
            .and("quantityInStock").gte(quantity));
        return changeStockQuantity(productId, quantity * -1, query);
    }

    private Result changeStockQuantity(UUID productId, Integer quantity, Query queryForUpdate) {

        Aggregation findProductQuantity = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("id").is(productId)),
            Aggregation.project("quantityInStock")
        );

        Document productBeforeUpdate = mongoOperations.aggregate(
            findProductQuantity, Product.class, Document.class
        ).getUniqueMappedResult();

        if (productBeforeUpdate == null) {
            throw new ProductNotFoundException(productId);
        }

        Integer previousQuantity = productBeforeUpdate.getInteger("quantityInStock");

        Update update = new Update()
            .inc("quantityInStock", quantity)
            .inc("version", 1)
            .set("updatedAt", OffsetDateTime.now());

        Product productUpdate = mongoOperations.findAndModify(
            queryForUpdate,
            update,
            new FindAndModifyOptions().returnNew(true),
            Product.class
        );

        if (productUpdate == null) {
            throw new StockUpdateFailed(String.format("Failed to update stock of product %s ", productId));
        }

        Integer newQuantity = productUpdate.getQuantityInStock();

        return new Result(productUpdate.getId(), previousQuantity, newQuantity);
    }
}