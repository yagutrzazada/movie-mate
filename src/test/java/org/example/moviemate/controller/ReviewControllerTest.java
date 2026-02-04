package org.example.moviemate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.moviemate.model.dto.ReviewDTO;
import org.example.moviemate.service.intf.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ReviewControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private ReviewService reviewService;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void addReview_ShouldReturnOk() throws Exception {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setMovieId(1L);
        reviewDTO.setRating(5.0);

        mockMvc.perform(post("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reviewDTO))
                        .principal(() -> "testUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Rəy uğurla əlavə edildi!"));
    }

    @Test
    void getReviewsByMovie_ShouldReturnList() throws Exception {
        mockMvc.perform(get("/api/v1/reviews/movie/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }
}