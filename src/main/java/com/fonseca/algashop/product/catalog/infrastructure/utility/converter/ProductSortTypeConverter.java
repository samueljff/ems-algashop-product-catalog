package com.fonseca.algashop.product.catalog.infrastructure.utility.converter;

import com.fonseca.algashop.product.catalog.application.product.query.ProductFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductSortTypeConverter implements Converter<String, ProductFilter.SortType> {
    @Override
    public ProductFilter.SortType convert(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        try {
            return ProductFilter.SortType.valueOf(source.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null; // valor inválido -> cai no default via getSortByPropertyOrDefault()
        }
    }
}