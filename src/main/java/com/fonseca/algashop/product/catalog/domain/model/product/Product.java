package com.fonseca.algashop.product.catalog.domain.model.product;

import com.fonseca.algashop.product.catalog.domain.model.IdGenerator;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Document(collection = "products")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {

    @EqualsAndHashCode.Include
    @Id
    private UUID id;
    private String name;
    private String brand;
    private String description;
    private Integer quantityInStock;
    private Boolean enabled;
    private BigDecimal regularPrice;
    private BigDecimal salePrice;

    @Version
    private Long version;

    @CreatedDate
    private OffsetDateTime addedAt;

    @LastModifiedDate
    private OffsetDateTime updatedAt;

    @CreatedBy
    private UUID createByUserId;

    @LastModifiedBy
    private UUID lastModifyByUserId;

    @Builder
    public Product(String name, String brand, String description, Boolean enabled, BigDecimal regularPrice, BigDecimal salePrice) {
        this.id = IdGenerator.generateTimeBasedUUID();
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.enabled = enabled;
        this.regularPrice = regularPrice;
        this.salePrice = salePrice;
    }
}
