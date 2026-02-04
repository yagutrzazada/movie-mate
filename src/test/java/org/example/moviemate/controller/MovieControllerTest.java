package org.example.moviemate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.moviemate.model.dto.MovieDTO;
import org.example.moviemate.model.enums.Genre;
import org.example.moviemate.service.intf.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class MovieControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private MovieService movieService;
    @Autowired private ObjectMapper objectMapper;

    @Test
    @WithMockUser(authorities = "ADMIN")
    void createMovie_ShouldReturnOk() throws Exception {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setTitle("Matrix");
        movieDTO.setGenre(Genre.ACTION);
        movieDTO.setReleaseDate(LocalDate.now());

        when(movieService.createMovie(any())).thenReturn(movieDTO);

        mockMvc.perform(post("/api/v1/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void deleteMovie_ShouldReturnOk() throws Exception {
        mockMvc.perform(delete("/api/v1/movies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Film silindi!"));
    }

    @Test
    void getAllMovies_ShouldReturnList() throws Exception {
        when(movieService.getAllMovies()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }
}