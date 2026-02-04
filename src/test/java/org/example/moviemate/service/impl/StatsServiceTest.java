package org.example.moviemate.service.impl;

import org.example.moviemate.dao.repository.DiaryRepository;
import org.example.moviemate.dao.repository.MovieRepository;
import org.example.moviemate.dao.repository.ReviewRepository;
import org.example.moviemate.dao.repository.UserRepository;
import org.example.moviemate.model.dto.SystemStatsDTO;
import org.example.moviemate.model.dto.UserStatsDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatsServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private MovieRepository movieRepository;
    @Mock private ReviewRepository reviewRepository;
    @Mock private DiaryRepository diaryRepository;

    @InjectMocks
    private StatsServiceImpl statsService;

    @Test
    void getUserStats_ShouldReturnCorrectStats() {
        Long userId = 1L;
        when(diaryRepository.countMoviesByUserIdAndWatchedAtAfter(eq(userId), any(LocalDateTime.class)))
                .thenReturn(10L);
        when(reviewRepository.countByUserIdAndCreatedAtAfter(eq(userId), any(LocalDateTime.class)))
                .thenReturn(4L);
        when(diaryRepository.findFavoriteGenreByUserId(userId))
                .thenReturn(Optional.of("Drama"));

        UserStatsDTO result = statsService.getUserStats(userId);

        assertEquals(10L, result.getWatchedMoviesLastYear());
        assertEquals(4L, result.getReviewsLastYear());
        assertEquals("Drama", result.getFavoriteGenre());
    }

    @Test
    void getSystemStats_ShouldReturnAggregatedData() {
        when(userRepository.count()).thenReturn(100L);
        when(movieRepository.count()).thenReturn(500L);
        when(userRepository.countByCreatedAtAfter(any(LocalDateTime.class))).thenReturn(20L);
        when(movieRepository.countByCreatedAtAfter(any(LocalDateTime.class))).thenReturn(50L);

        SystemStatsDTO result = statsService.getSystemStats();
        assertEquals(100L, result.getTotalUsers());
        assertEquals(500L, result.getTotalMovies());
        assertEquals(20L, result.getNewUsersLastYear());
        assertEquals(50L, result.getNewMoviesLastYear());
    }
}