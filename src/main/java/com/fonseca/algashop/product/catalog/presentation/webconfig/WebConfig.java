package com.fonseca.algashop.product.catalog.presentation.webconfig;

import com.fonseca.algashop.product.catalog.infrastructure.utility.converter.CategorySortTypeConverter;
import com.fonseca.algashop.product.catalog.infrastructure.utility.converter.ProductSortTypeConverter;
import com.fonseca.algashop.product.catalog.infrastructure.utility.converter.SortDirectionConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final SortDirectionConverter sortDirectionConverter;
    private final CategorySortTypeConverter categorySortTypeConverter;
    private final ProductSortTypeConverter productSortTypeConverter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(sortDirectionConverter);
        registry.addConverter(categorySortTypeConverter);
        registry.addConverter(productSortTypeConverter);
    }
}