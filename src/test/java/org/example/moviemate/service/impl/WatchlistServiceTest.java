package org.example.moviemate.service.impl;

import org.example.moviemate.dao.entity.MovieEntity;
import org.example.moviemate.dao.entity.UserEntity;
import org.example.moviemate.dao.entity.WatchlistEntity;
import org.example.moviemate.dao.repository.MovieRepository;
import org.example.moviemate.dao.repository.UserRepository;
import org.example.moviemate.dao.repository.WatchlistRepository;
import org.example.moviemate.model.dto.WatchlistDTO;
import org.example.moviemate.model.enums.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WatchlistServiceTest {

    @Mock private WatchlistRepository watchlistRepository;
    @Mock private MovieRepository movieRepository;
    @Mock private UserRepository userRepository;
    @Mock private org.example.moviemate.mapper.WatchlistMapper watchlistMapper;

    @InjectMocks
    private WatchlistServiceImpl watchlistService;

    @Test
    void addMovieToWatchlist_ShouldThrowException_WhenDuplicate() {
        Long userId = 1L;
        Long movieId = 100L;
        UserEntity user = UserEntity.builder().id(userId).username("user").role(Role.USER).build();
        MovieEntity movie = MovieEntity.builder().id(movieId).build();

        WatchlistEntity watchlist = new WatchlistEntity();
        watchlist.setMovies(new ArrayList<>(List.of(movie)));

        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(watchlistRepository.findByUserId(userId)).thenReturn(Optional.of(watchlist));

        assertThrows(RuntimeException.class, () -> watchlistService.addMovieToWatchlist(null, movieId, "user"));
    }
}