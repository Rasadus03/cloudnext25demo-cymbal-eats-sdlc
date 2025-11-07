package com.cymbaleats.order.management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderUtils orderUtils;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private OrderManagementController orderManagementController;

    @Test
    void getUserOrders() throws Exception {
        when(orderUtils.getUserOrder(any(User.class))).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/order-mgmt-api/list-orders").param("user-id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void getUserOrderDetailss() throws Exception {
        when(orderUtils.getOrderDetails(any(Order.class))).thenReturn(new Order());
        mockMvc.perform(post("/order-mgmt-api/get-order-details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void ping() throws Exception {
        mockMvc.perform(get("/order-mgmt-api"))
                .andExpect(status().isOk());
    }

    @Test
    void placeAnOrder() throws Exception {
        when(orderUtils.insertOrder(any(Order.class))).thenReturn(1L);
        when(restTemplate.postForObject(any(String.class), any(), any(Class.class))).thenReturn("success");
        mockMvc.perform(post("/order-mgmt-api/place-order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"1\"}"))
                .andExpect(status().isOk());
    }
}
