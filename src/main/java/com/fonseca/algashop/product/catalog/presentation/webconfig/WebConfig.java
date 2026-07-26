package com.fonseca.algashop.product.catalog.presentation.webconfig;

import com.fonseca.algashop.product.catalog.infrastructure.utility.CategorySortTypeConverter;
import com.fonseca.algashop.product.catalog.infrastructure.utility.SortDirectionConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final SortDirectionConverter sortDirectionConverter;
    private final CategorySortTypeConverter categorySortTypeConverter;

    public WebConfig(SortDirectionConverter sortDirectionConverter,
                      CategorySortTypeConverter categorySortTypeConverter) {
        this.sortDirectionConverter = sortDirectionConverter;
        this.categorySortTypeConverter = categorySortTypeConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(sortDirectionConverter);
        registry.addConverter(categorySortTypeConverter);
    }
}