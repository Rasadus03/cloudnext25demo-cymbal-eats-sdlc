package com.cymbaleats.order.management;

import com.google.cloud.spanner.ResultSet;
import com.google.cloud.spanner.Statement;
import com.google.cloud.spring.data.spanner.core.SpannerTemplate;
import com.google.cloud.spring.data.spanner.core.admin.SpannerDatabaseAdminTemplate;
import com.google.cloud.spring.data.spanner.core.admin.SpannerSchemaUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class OrderUtilsTest {

    @MockBean
    private SpannerTemplate spannerTemplate;

    @MockBean
    private SpannerSchemaUtils spannerSchemaUtils;

    @MockBean
    private SpannerDatabaseAdminTemplate spannerDatabaseAdminTemplate;

    @Autowired
    private OrderUtils orderUtils;

    @Test
    void insertOrder() {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("orderId")).thenReturn(1L);
        when(spannerTemplate.executeQuery(any(Statement.class), any())).thenReturn(resultSet);

        Order order = new Order();
        order.setUserId("1");
        order.setOrderItems(Collections.emptyList());
        order.setShippingAddress(Collections.emptyList());
        assertEquals(2L, orderUtils.insertOrder(order));
    }

    @Test
    void getOrderDetails() {
        when(spannerTemplate.query(any(Class.class), any(Statement.class), any())).thenReturn(Collections.emptyList());
        Order order = new Order();
        order.setUserId("1");
        assertEquals(0, orderUtils.getOrderDetails(order).getOrderItems().size());
    }

    @Test
    void getUserOrder() {
        when(spannerTemplate.query(any(Class.class), any(Statement.class), any())).thenReturn(Collections.emptyList());
        assertEquals(0, orderUtils.getUserOrder(new User("1")).size());
    }
}
