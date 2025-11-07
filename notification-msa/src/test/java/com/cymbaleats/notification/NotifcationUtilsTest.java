package com.cymbaleats.notification;

import com.google.cloud.spanner.ResultSet;
import com.google.cloud.spanner.Statement;
import com.google.cloud.spring.data.spanner.core.SpannerTemplate;
import com.google.cloud.spring.data.spanner.core.admin.SpannerDatabaseAdminTemplate;
import com.google.cloud.spring.data.spanner.core.admin.SpannerSchemaUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NotifcationUtilsTest {

    @Mock
    private SpannerTemplate spannerTemplate;

    @Mock
    private SpannerSchemaUtils spannerSchemaUtils;

    @Mock
    private SpannerDatabaseAdminTemplate spannerDatabaseAdminTemplate;

    private NotifcationUtils notifcationUtils;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        notifcationUtils = new NotifcationUtils(spannerTemplate, spannerSchemaUtils, spannerDatabaseAdminTemplate);
    }

    @Test
    void insertNotification() {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("notificationId")).thenReturn(1L);
        when(spannerTemplate.executeQuery(any(Statement.class), any())).thenReturn(resultSet);

        notifcationUtils.insertNotification(new Notification("1", 1L, 1L, "test", Notification.Status.Unread, null));
    }

    @Test
    void updateNotificationStatus() {
        notifcationUtils.updateNotificationStatus(new Notification("1", 1L, 1L, "test", Notification.Status.Unread, null));
    }

    @Test
    void getUserNotification() {
        when(spannerTemplate.query(any(Class.class), any(Statement.class), any())).thenReturn(Collections.emptyList());
        assertEquals(0, notifcationUtils.getUserNotification(new User("1")).size());
    }

    @Test
    void getUserUnreadNotificationCount() {
        when(spannerTemplate.query(any(Class.class), any(Statement.class), any())).thenReturn(Collections.emptyList());
        assertEquals(0, notifcationUtils.getUserUnreadNotificationCount(new User("1")));
    }
}
