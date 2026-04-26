package com.fonseca.algashop.product.catalog.contract.base;

import com.fonseca.algashop.product.catalog.application.PageModel;
import com.fonseca.algashop.product.catalog.application.ResourceNotFoundException;
import com.fonseca.algashop.product.catalog.application.category.managment.CategoryInput;
import com.fonseca.algashop.product.catalog.application.category.managment.CategoryManagementService;
import com.fonseca.algashop.product.catalog.application.category.query.CategoryDetailOutput;
import com.fonseca.algashop.product.catalog.application.category.query.CategoryOutputTestDataBuilder;
import com.fonseca.algashop.product.catalog.application.category.query.CategoryQueryService;
import com.fonseca.algashop.product.catalog.presentation.CategoryController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@WebMvcTest(controllers = CategoryController.class)
public class CategoryBase {

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private CategoryQueryService categoryQueryService;

    @MockitoBean
    private CategoryManagementService categoryManagementService;

    public static final UUID validCategoryId = UUID.fromString("a3c91d7f-52be-4e8a-b017-9f2e847d1c34");
    public static final UUID invalidCategoryId = UUID.fromString("00000000-0000-0000-0000-000000000000");

    public static final UUID createdCategoryId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(MockMvcBuilders.webAppContextSetup(context)
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8).build());

        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();

        Mockito.when(categoryQueryService.filter(Mockito.anyInt(), Mockito.anyInt()))
                .then((answer)-> {
                    Integer size = answer.getArgument(0);
                    return PageModel.<CategoryDetailOutput>builder()
                            .number(0)
                            .size(size)
                            .totalPages(1)
                            .totalElements(2)
                            .content(
                                    List.of(
                                            CategoryOutputTestDataBuilder.aCategory().build(),
                                            CategoryOutputTestDataBuilder.aCategoryAlt().build()
                                    )
                            ).build();
                });

        mockFindCategoryById(validCategoryId);
        mockCreateCategory();
        mockFindCategoryById(createdCategoryId);
        mockFindCategoryByIdNotFound();
        mockUpdateCategory();
        mockUpdateCategoryNotFound();
        mockDeleteCategory();
        mockDeleteCategoryNotFound();
    }

    private void mockDeleteCategoryNotFound() {
        Mockito.doThrow(new ResourceNotFoundException())
            .when(categoryManagementService).disable(invalidCategoryId);
    }

    private void mockDeleteCategory() {
        Mockito.doNothing().when(categoryManagementService).disable(validCategoryId);
    }

    private void mockUpdateCategoryNotFound() {
        Mockito.doThrow(new ResourceNotFoundException())
            .when(categoryManagementService).update(eq(invalidCategoryId), any(CategoryInput.class));
    }

    private void mockUpdateCategory() {
        Mockito.doNothing().when(categoryManagementService)
            .update(eq(validCategoryId), any(CategoryInput.class));
    }

    private void mockFindCategoryByIdNotFound() {
        Mockito.when(categoryQueryService.findById(invalidCategoryId))
            .thenThrow(new ResourceNotFoundException());
    }

    private void mockCreateCategory() {
        Mockito.when(categoryManagementService.create(Mockito.any(CategoryInput.class)))
                .thenReturn(createdCategoryId);
    }

    private void mockFindCategoryById(UUID validCategoryId) {
        Mockito.when(categoryQueryService.findById(validCategoryId))
            .thenReturn(CategoryOutputTestDataBuilder.aCategory().id(validCategoryId).build());
    }
}