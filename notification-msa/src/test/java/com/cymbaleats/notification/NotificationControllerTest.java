package com.cymbaleats.notification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NotificationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NotifcationUtils notificationUtils;

    @InjectMocks
    private NotificationController notificationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(notificationController).build();
    }

    @Test
    void getUserNotifications() throws Exception {
        when(notificationUtils.getUserNotification(any(User.class))).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/notification/list-notifications").param("user-id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void getUserUnreadNotificationsCount() throws Exception {
        when(notificationUtils.getUserUnreadNotificationCount(any(User.class))).thenReturn(0L);
        mockMvc.perform(get("/notification/get-notifications-unread-count").param("user-id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void ping() throws Exception {
        mockMvc.perform(get("/notification"))
                .andExpect(status().isOk());
    }

    @Test
    void addNotification() throws Exception {
        mockMvc.perform(post("/notification/addnotification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void updateNotificationStatus() throws Exception {
        mockMvc.perform(post("/notification/update-notification-status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }
}
