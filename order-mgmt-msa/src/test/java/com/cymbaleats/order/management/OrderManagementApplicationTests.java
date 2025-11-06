package com.cymbaleats.order.management;

import com.google.cloud.spring.data.spanner.core.SpannerTemplate;
import com.google.cloud.spring.data.spanner.core.admin.SpannerDatabaseAdminTemplate;
import com.google.cloud.spring.data.spanner.core.admin.SpannerSchemaUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class OrderManagementApplicationTests {

	@MockBean
	private SpannerTemplate spannerTemplate;

	@MockBean
	private SpannerSchemaUtils spannerSchemaUtils;

	@MockBean
	private SpannerDatabaseAdminTemplate spannerDatabaseAdminTemplate;

	@Test
	void contextLoads() {
	}

}
