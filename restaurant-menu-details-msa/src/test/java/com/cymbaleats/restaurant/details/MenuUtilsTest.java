package com.cymbaleats.restaurant.details;

import com.google.cloud.spring.data.spanner.core.SpannerTemplate;
import com.google.cloud.spring.data.spanner.core.admin.SpannerDatabaseAdminTemplate;
import com.google.cloud.spring.data.spanner.core.admin.SpannerSchemaUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MenuUtilsTest {

    @Mock
    private SpannerTemplate spannerTemplate;

    @Mock
    private SpannerSchemaUtils spannerSchemaUtils;

    @Mock
    private SpannerDatabaseAdminTemplate spannerDatabaseAdminTemplate;

    private MenuUtils menuUtils;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        menuUtils = new MenuUtils(spannerTemplate, spannerSchemaUtils, spannerDatabaseAdminTemplate);
    }

    @Test
    void insertMenuItem() {
        menuUtils.insertMenuItem(new MenuItem(1L, 1L, "test", "test", "test", 1.0));
    }

    @Test
    void getRestaurantMenu() {
        when(spannerTemplate.query(any(Class.class), any(), any())).thenReturn(Collections.emptyList());
        assertEquals(0, menuUtils.getRestaurantMenu(new Restaurant(1L)).size());
    }
}
