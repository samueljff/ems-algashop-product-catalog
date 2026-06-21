package com.fonseca.algashop.product.catalog;

import com.fonseca.algashop.product.catalog.application.category.managment.CategoryManagementService;
import com.fonseca.algashop.product.catalog.application.category.query.CategoryQueryService;
import com.fonseca.algashop.product.catalog.application.product.query.ProductQueryService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class ProductCatalogApplicationTests {

	@MockitoBean
	private CategoryQueryService categoryQueryService;

	@MockitoBean
	private CategoryManagementService categoryManagementService;

	@MockitoBean
	private ProductQueryService productQueryService;

	@Test
	void contextLoads() {
	}

}
