package org.example.moviemate.service.impl;

import org.example.moviemate.dao.entity.MovieEntity;
import org.example.moviemate.dao.repository.MovieRepository;
import org.example.moviemate.mapper.MovieMapper;
import org.example.moviemate.model.dto.MovieDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock private MovieRepository movieRepository;
    @Mock private MovieMapper movieMapper;

    @InjectMocks
    private MovieServiceImpl movieService;

    @Test
    void deleteMovie_ShouldThrowException_WhenMovieNotFound() {
        when(movieRepository.existsById(1L)).thenReturn(false);

        assertThrows(org.example.moviemate.exception.NotFoundException.class,
                () -> movieService.deleteMovie(1L));
    }
}