package com.cymbaleats.restaurants;

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

class RestaurantUtilsTest {

    @Mock
    private SpannerTemplate spannerTemplate;

    @Mock
    private SpannerSchemaUtils spannerSchemaUtils;

    @Mock
    private SpannerDatabaseAdminTemplate spannerDatabaseAdminTemplate;

    private RestaurantUtils restaurantUtils;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurantUtils = new RestaurantUtils(spannerTemplate, spannerSchemaUtils, spannerDatabaseAdminTemplate);
    }

    @Test
    void insertRestuarant() {
        restaurantUtils.insertRestuarant(new Restaurant("test", 1L, "test", "test", Collections.emptyList()));
    }

    @Test
    void getAllRestuarants() {
        when(spannerTemplate.query(any(Class.class), any(), any())).thenReturn(Collections.emptyList());
        assertEquals(0, restaurantUtils.getAllRestuarants().size());
    }
}
