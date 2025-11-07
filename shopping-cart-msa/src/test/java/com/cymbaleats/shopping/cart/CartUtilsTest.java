package com.cymbaleats.shopping.cart;

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

class CartUtilsTest {

    @Mock
    private SpannerTemplate spannerTemplate;

    @Mock
    private SpannerSchemaUtils spannerSchemaUtils;

    @Mock
    private SpannerDatabaseAdminTemplate spannerDatabaseAdminTemplate;

    private CartUtils cartUtils;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartUtils = new CartUtils(spannerTemplate, spannerSchemaUtils, spannerDatabaseAdminTemplate);
    }

    @Test
    void insertUser() {
        cartUtils.insertUser(new User());
    }

    @Test
    void insertCartItem() {
        cartUtils.insertCartItem(new ShoppingCartItem());
    }

    @Test
    void updateCartItem() {
        cartUtils.updateCartItem(new ShoppingCartItem());
    }

    @Test
    void deleteCartItem() {
        cartUtils.deleteCartItem(new ShoppingCartItem());
    }

    @Test
    void clearUserCart() {
        when(spannerTemplate.query(any(Class.class), any(), any())).thenReturn(Collections.emptyList());
        cartUtils.clearUserCart(new User());
    }

    @Test
    void getUserCart() {
        when(spannerTemplate.query(any(Class.class), any(), any())).thenReturn(Collections.emptyList());
        assertEquals(0, cartUtils.getUserCart(new User()).size());
    }

    @Test
    void getMenuItem() {
        when(spannerTemplate.query(any(Class.class), any(), any())).thenReturn(Collections.emptyList());
        assertEquals(null, cartUtils.getMenuItem(new ShoppingCartItem()));
    }

    @Test
    void getUserCartItemCount() {
        when(spannerTemplate.query(any(Class.class), any(), any())).thenReturn(Collections.emptyList());
        assertEquals(0, cartUtils.getUserCartItemCount(new User()));
    }
}
