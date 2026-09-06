package com.indiana.foodlocator.security;

import com.indiana.foodlocator.service.FoodLocationService;
import com.indiana.foodlocator.service.UserService;
import com.indiana.foodlocator.service.FavoriteService;
import com.indiana.foodlocator.config.SecurityConfig;
import com.indiana.foodlocator.service.CustomUserDetailsService;
import org.springframework.context.annotation.Import;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@WebMvcTest
@Import(SecurityConfig.class)
class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FoodLocationService foodLocationService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private FavoriteService favoriteService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    // Test 1. Test that the home page is accessible without authentication.

    @Test
    void homePageIsAccessibleWithoutAuthentication() throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    // Test 2. Test that favorite requires authentication and returns 401
    // Unauthorized when accessed without authentication.

    @Test
    void favoritesPageRequiresAuthentication() throws Exception {

        mockMvc.perform(get("/favorites"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    // Test 3: verify a normal USER cannot access the admin portal.
    // This test assumes that the admin portal is located at /admin.

    @Test
    @WithMockUser(username = "user@example.com", roles = "USER")
    void regularUserCannotAccessAdminPage() throws Exception {

        mockMvc.perform(get("/admin/locations/new"))
                .andExpect(status().isForbidden());
    }

    // Test 4: verify an ADMIN can access the admin portal.
    // This test assumes that the admin portal is located at /admin.

    @Test
    @WithMockUser(username = "admin@example.com", roles = "ADMIN")
    void adminCanAccessAdminPage() throws Exception {

        mockMvc.perform(get("/admin/locations/new"))
                .andExpect(status().isOk());
    }

    // Test 5: Authenticated USER can access Favorites
    // This test assumes that the favorites page is located at /favorites.

    @Test
    @WithMockUser(username = "user@example.com", roles = "USER")
    void authenticatedUserCanAccessFavoritesPage() throws Exception {

        mockMvc.perform(get("/favorites"))
                .andExpect(status().isOk());
    }
}
