package com.cymbaleats.restaurant.details;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantDetailsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MenuUtils menuUtils;

    @InjectMocks
    private RestaurantDetailsController restaurantDetailsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantDetailsController).build();
    }

    @Test
    void getRestaurantMenu() throws Exception {
        when(menuUtils.getRestaurantMenu(any(Restaurant.class))).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/restaurant-details-api/restaurant-menu").param("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void ping() throws Exception {
        mockMvc.perform(get("/restaurant-details-api"))
                .andExpect(status().isOk());
    }
}
