package com.cymbaleats.restaurants;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RestaurantUtils restaurantUtils;

    @InjectMocks
    private RestaurantsController restaurantsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantsController).build();
    }

    @Test
    void getAllRestaurants() throws Exception {
        when(restaurantUtils.getAllRestuarants()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/restaurants-api/restaurants"))
                .andExpect(status().isOk());
    }

    @Test
    void ping() throws Exception {
        mockMvc.perform(get("/restaurants-api"))
                .andExpect(status().isOk());
    }
}
