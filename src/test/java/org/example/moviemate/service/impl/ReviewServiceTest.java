package org.example.moviemate.service.impl;

import org.example.moviemate.dao.entity.MovieEntity;
import org.example.moviemate.dao.entity.ReviewEntity;
import org.example.moviemate.dao.entity.UserEntity;
import org.example.moviemate.dao.repository.MovieRepository;
import org.example.moviemate.dao.repository.ReviewRepository;
import org.example.moviemate.dao.repository.UserRepository;
import org.example.moviemate.mapper.ReviewMapper;
import org.example.moviemate.model.dto.ReviewDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {


    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ReviewMapper reviewMapper;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private UserEntity user;
    private MovieEntity movie;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setId(1L);
        user.setUsername("testUser");

        movie = new MovieEntity();
        movie.setId(10L);

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void addReview_ShouldCopyLastRating_WhenNewRatingIsNull() {
        ReviewDTO dto = new ReviewDTO();
        dto.setMovieId(10L);
        dto.setRating(null);
        dto.setComment("Köhnə reytinqlə davam");

        ReviewEntity lastReview = new ReviewEntity();
        lastReview.setRating(9.0);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(movieRepository.findById(10L)).thenReturn(Optional.of(movie));
        when(reviewRepository.findTopByUserIdAndMovieIdOrderByCreatedAtDesc(1L, 10L))
                .thenReturn(Optional.of(lastReview));

        reviewService.addReview(dto);

        verify(reviewRepository).save(argThat(entity -> entity.getRating().equals(9.0)));
    }

    @Test
    void addReview_ShouldThrowException_WhenFirstReviewHasNoRating() {
        ReviewDTO dto = new ReviewDTO();
        dto.setMovieId(10L);
        dto.setRating(null);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(movieRepository.findById(10L)).thenReturn(Optional.of(movie));
        when(reviewRepository.findTopByUserIdAndMovieIdOrderByCreatedAtDesc(1L, 10L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> reviewService.addReview(dto));
    }
    @Test
    void deleteReview_ShouldThrowException_WhenUserIsNotOwnerOrAdmin() {
        UserEntity owner = new UserEntity();
        owner.setId(99L);

        ReviewEntity review = new ReviewEntity();
        review.setUser(owner);
        review.setId(5L);

        UserEntity currentUser = new UserEntity();
        currentUser.setId(1L);
        currentUser.setRole(org.example.moviemate.model.enums.Role.USER);

        when(reviewRepository.findById(5L)).thenReturn(Optional.of(review));
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(currentUser));

        assertThrows(org.springframework.web.server.ResponseStatusException.class,
                () -> reviewService.deleteReview(5L, "testUser"));
    }

    @Test
    void deleteReview_ShouldSucceed_WhenUserIsAdmin() {
        user.setRole(org.example.moviemate.model.enums.Role.ADMIN);

        UserEntity owner = new UserEntity();
        owner.setId(99L);

        ReviewEntity review = new ReviewEntity();
        review.setUser(owner);
        review.setMovie(movie);

        when(userRepository.findByUsername("adminUser")).thenReturn(Optional.of(user));
        when(reviewRepository.findById(5L)).thenReturn(Optional.of(review));

        reviewService.deleteReview(5L, "adminUser");

        verify(reviewRepository).delete(review);
    }
}