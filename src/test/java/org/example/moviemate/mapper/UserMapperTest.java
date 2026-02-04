package org.example.moviemate.mapper;

import org.example.moviemate.dao.entity.DiaryEntity;
import org.example.moviemate.dao.entity.MovieEntity;
import org.example.moviemate.dao.entity.UserEntity;
import org.example.moviemate.dao.entity.WatchlistEntity;
import org.example.moviemate.model.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void toUserDTO_ShouldMapTitlesCorrectly() {
        MovieEntity movie1 = MovieEntity.builder().title("Inception").build();
        MovieEntity movie2 = MovieEntity.builder().title("Interstellar").build();

        WatchlistEntity watchlist = WatchlistEntity.builder()
                .movies(List.of(movie1))
                .build();

        DiaryEntity diary = DiaryEntity.builder()
                .movies(List.of(movie2))
                .build();

        UserEntity userEntity = UserEntity.builder()
                .username("testUser")
                .watchlists(List.of(watchlist))
                .diaries(List.of(diary))
                .build();

        UserDTO dto = userMapper.toUserDTO(userEntity);

        assertEquals("testUser", dto.getUsername());
        assertTrue(dto.getWatchlist().contains("Inception"));
        assertTrue(dto.getDiary().contains("Interstellar"));
    }
}