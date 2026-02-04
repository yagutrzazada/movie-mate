package org.example.moviemate.controller;

import org.example.moviemate.service.intf.DiaryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class DiaryControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private DiaryService diaryService;

    @Test
    void logMovie_ShouldReturnSuccess() throws Exception {
        mockMvc.perform(post("/api/v1/diary/log")
                        .param("movieId", "1")
                        .principal(() -> "testUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    void getUserDiary_ShouldReturnList() throws Exception {
        when(diaryService.getUserDiary(any(), anyString())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/diary")
                        .principal(() -> "testUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void removeMovie_ShouldReturnOk() throws Exception {
        mockMvc.perform(delete("/api/v1/diary/remove/1")
                        .principal(() -> "testUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Film tarixçədən silindi!"));
    }
}