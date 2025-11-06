package com.cymbaleats.shopping.cart;

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

class ShoppingCartControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CartUtils cartUtils;

    @InjectMocks
    private ShoppingCartController shoppingCartController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(shoppingCartController).build();
    }

    @Test
    void addShoppingCartItem() throws Exception {
        mockMvc.perform(post("/shopping-cart-api/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void getUserShoppingCart() throws Exception {
        when(cartUtils.getUserCart(any(User.class))).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/shopping-cart-api/view-shopping-cart").param("user-id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void getCartItemCount() throws Exception {
        when(cartUtils.getUserCartItemCount(any(User.class))).thenReturn(0L);
        mockMvc.perform(get("/shopping-cart-api/get-cart-item-count").param("user-id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void ping() throws Exception {
        mockMvc.perform(get("/shopping-cart-api"))
                .andExpect(status().isOk());
    }

    @Test
    void addShoppingCartItem1() throws Exception {
        when(cartUtils.getMenuItem(any(ShoppingCartItem.class))).thenReturn(null);
        mockMvc.perform(post("/shopping-cart-api/add-shopping-cart-item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void updateShoppingCartItemQuantity() throws Exception {
        mockMvc.perform(post("/shopping-cart-api/update-shopping-cart-item-quantity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void removeShoppingCartItem() throws Exception {
        mockMvc.perform(post("/shopping-cart-api/remove-shopping-cart-item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void clearShoppingCart() throws Exception {
        mockMvc.perform(post("/shopping-cart-api/clear-shopping-cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }
}
