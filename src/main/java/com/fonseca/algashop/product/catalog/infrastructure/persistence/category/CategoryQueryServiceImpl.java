package com.fonseca.algashop.product.catalog.infrastructure.persistence.category;

import com.fonseca.algashop.product.catalog.application.category.query.CategoryDetailOutput;
import com.fonseca.algashop.product.catalog.application.category.query.CategoryFilter;
import com.fonseca.algashop.product.catalog.application.category.query.CategoryQueryService;
import com.fonseca.algashop.product.catalog.application.utility.Mapper;
import com.fonseca.algashop.product.catalog.application.utility.PageModel;
import com.fonseca.algashop.product.catalog.domain.model.category.Category;
import com.fonseca.algashop.product.catalog.domain.model.category.CategoryNotFoundException;
import com.fonseca.algashop.product.catalog.domain.model.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryQueryServiceImpl implements CategoryQueryService {
    private final CategoryRepository categoryRepository;
    private final Mapper mapper;
    private final MongoOperations mongoOperations;
    private static final String findWordRegex = "(?i)%s";

    @Override
    public PageModel<CategoryDetailOutput> filter(CategoryFilter filter) {
        Query query = queryWith(filter);

        long totalItems = mongoOperations.count(query, Category.class);
        Sort sort = sortWith(filter);

        PageRequest pageRequest = PageRequest.of(filter.getPage(), filter.getSize(), sort);

        Query pagedQuery = query.with(pageRequest);

        List<Category> categories;
        int totalPages = 0;
        if (totalItems > 0) {
            categories = mongoOperations.find(pagedQuery, Category.class);
            totalPages = (int) Math.ceil((double) totalItems / pageRequest.getPageSize());
        } else {
            categories = new ArrayList<>();
        }

        List<CategoryDetailOutput> categoryDetailOutputs = categories.stream()
            .map(c -> mapper.convert(c, CategoryDetailOutput.class)).collect(Collectors.toList());
        return PageModel.<CategoryDetailOutput>builder()
            .content(categoryDetailOutputs)
            .number(pageRequest.getPageNumber())
            .size(pageRequest.getPageSize())
            .totalElements(totalItems)
            .totalPages(totalPages)
            .build();
    }

    @Override
    public CategoryDetailOutput findById(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
        return mapper.convert(category, CategoryDetailOutput.class);
    }

    @Override
    public OffsetDateTime lasModified() {
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.group().max("updatedAt").as("lastModified")
        );

        AggregationResults<Document> result = mongoOperations.aggregate(aggregation, "categories", Document.class);

        Document document = result.getUniqueMappedResult();

        if (document == null) {
            return OffsetDateTime.now();
        }

        return document.getDate("lastModified").toInstant().atOffset(ZoneOffset.UTC);
    }

    private Sort sortWith(CategoryFilter filter) {
        return Sort.by(filter.getSortDirectionOrDefault(), filter.getSortByPropertyOrDefault().getPropertyName());
    }

    private Query queryWith(CategoryFilter filter) {
        Query query = new Query();

        if (filter.getEnabled() != null) {
            query.addCriteria(Criteria.where("enabled").is(filter.getEnabled()));
        }

        if (StringUtils.isNotBlank(filter.getName())) {
            String escapedName = Pattern.quote(filter.getName()).trim();
            String regexExpression = String.format(findWordRegex, escapedName);
            query.addCriteria(
                Criteria.where("name").regex(regexExpression)
            );
        }
        return query;
    }
}
