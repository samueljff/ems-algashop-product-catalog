package com.fonseca.algashop.product.catalog.infrastructure.utility.converter;

import com.fonseca.algashop.product.catalog.application.category.query.CategoryFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategorySortTypeConverter implements Converter<String, CategoryFilter.SortType> {
    @Override
    public CategoryFilter.SortType convert(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        try {
            return CategoryFilter.SortType.valueOf(source.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null; // valor inválido -> cai no default via getSortByPropertyOrDefault()
        }
    }
}