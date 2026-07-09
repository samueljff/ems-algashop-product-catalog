package com.fonseca.algashop.product.catalog.infrastructure.persistence.category;

import com.fonseca.algashop.product.catalog.application.ResourceNotFoundException;
import com.fonseca.algashop.product.catalog.application.category.query.CategoryDetailOutput;
import com.fonseca.algashop.product.catalog.application.category.query.CategoryQueryService;
import com.fonseca.algashop.product.catalog.application.utility.Mapper;
import com.fonseca.algashop.product.catalog.application.utility.PageModel;
import com.fonseca.algashop.product.catalog.domain.model.category.Category;
import com.fonseca.algashop.product.catalog.domain.model.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryQueryServiceImpl implements CategoryQueryService {
    private final CategoryRepository categoryRepository;
    private final Mapper mapper;

    @Override
    public PageModel<CategoryDetailOutput> filter(Integer size, Integer number) {
        return null;
    }

    @Override
    public CategoryDetailOutput findById(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException());
        return mapper.convert(category, CategoryDetailOutput.class);
    }
}
