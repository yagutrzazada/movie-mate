package org.example.moviemate.service.impl;

import org.example.moviemate.dao.entity.DiaryEntity;
import org.example.moviemate.dao.entity.MovieEntity;
import org.example.moviemate.dao.entity.UserEntity;
import org.example.moviemate.dao.repository.DiaryRepository;
import org.example.moviemate.dao.repository.MovieRepository;
import org.example.moviemate.dao.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiaryServiceTest {

    @Mock private DiaryRepository diaryRepository;
    @Mock private MovieRepository movieRepository;
    @Mock private UserRepository userRepository;

    @InjectMocks
    private DiaryServiceImpl diaryService;

    @Test
    void logMovie_ShouldCreateNewDiary_WhenNotExist() {
        UserEntity user = UserEntity.builder().id(1L).username("test").build();
        MovieEntity movie = MovieEntity.builder().id(5L).build();

        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        when(diaryRepository.findByUserId(1L)).thenReturn(Optional.empty());
        when(movieRepository.findById(5L)).thenReturn(Optional.of(movie));

        when(diaryRepository.save(any(DiaryEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        diaryService.logMovie(5L, "test");

        verify(diaryRepository, atLeastOnce()).save(any(DiaryEntity.class));
    }}