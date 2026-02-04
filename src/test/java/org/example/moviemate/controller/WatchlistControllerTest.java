package org.example.moviemate.controller;

import org.example.moviemate.service.intf.WatchlistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class WatchlistControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private WatchlistService watchlistService;

    @Test
    void addMovie_ShouldReturnOk() throws Exception {
        mockMvc.perform(post("/api/v1/watchlist/add/1")
                        .principal(() -> "testUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Film siyahıya əlavə edildi!"));
    }

    @Test
    void getWatchlist_ShouldReturnData() throws Exception {
        mockMvc.perform(get("/api/v1/watchlist").principal(() -> "testUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }
}