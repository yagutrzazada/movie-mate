package org.example.moviemate.mapper;

import org.example.moviemate.dao.entity.DiaryEntity;
import org.example.moviemate.dao.entity.MovieEntity;
import org.example.moviemate.dao.entity.UserEntity;
import org.example.moviemate.model.dto.DiaryDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class DiaryMapperTest {

    @Autowired
    private DiaryMapper diaryMapper;

    @Test
    void toDiaryDTO_ShouldMapCorrectly() {
        UserEntity user = UserEntity.builder().id(1L).build();
        MovieEntity movie = MovieEntity.builder().title("Gladiator").build();

        DiaryEntity entity = DiaryEntity.builder()
                .user(user)
                .movies(List.of(movie))
                .watchedAt(LocalDateTime.now())
                .build();

        DiaryDTO dto = diaryMapper.toDiaryDTO(entity);

        assertEquals(1L, dto.getUserId());
        assertEquals("Gladiator", dto.getMovies().get(0));
    }

    @Test
    void toDiaryEntity_ShouldIgnoreUserAndMovies() {
        DiaryDTO dto = new DiaryDTO();
        dto.setUserId(1L);
        dto.setMovies(List.of("Batman"));

        DiaryEntity entity = diaryMapper.toDiaryEntity(dto);

        assertNull(entity.getUser());
        assertNull(entity.getMovies());
    }
}