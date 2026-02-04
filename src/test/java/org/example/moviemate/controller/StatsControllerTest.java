package org.example.moviemate.controller;

import org.example.moviemate.model.dto.SystemStatsDTO;
import org.example.moviemate.model.dto.UserDTO;
import org.example.moviemate.service.intf.StatsService;
import org.example.moviemate.service.intf.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class StatsControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private StatsService statsService;
    @MockBean private UserService userService;

    @Test
    @WithMockUser(authorities = "ADMIN")
    void getSystemStats_ShouldReturnOk() throws Exception {
        when(statsService.getSystemStats()).thenReturn(new SystemStatsDTO());

        mockMvc.perform(get("/api/v1/stats/system"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    void getUserStats_ShouldReturnOk() throws Exception {
        UserDTO user = new UserDTO(); user.setId(1L);
        when(userService.getByUsername(anyString())).thenReturn(user);

        mockMvc.perform(get("/api/v1/stats/user").principal(() -> "testUser"))
                .andExpect(status().isOk());
    }
}