package org.example.moviemate.controller;

import org.example.moviemate.model.dto.UserDTO;
import org.example.moviemate.service.intf.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private UserService userService;

    @Test
    void getMyProfile_ShouldReturnUser() throws Exception {
        UserDTO user = new UserDTO();
        user.setUsername("testUser");
        when(userService.getByUsername(anyString())).thenReturn(user);

        mockMvc.perform(get("/api/v1/users/me").principal(() -> "testUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").value("testUser"));
    }
}