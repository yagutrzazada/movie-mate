package org.example.moviemate.mapper;

import org.example.moviemate.dao.entity.MovieEntity;
import org.example.moviemate.dao.entity.UserEntity;
import org.example.moviemate.dao.entity.WatchlistEntity;
import org.example.moviemate.model.dto.WatchlistDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class WatchlistMapperTest {

    @Autowired
    private WatchlistMapper watchlistMapper;

    @Test
    void toWatchlistDTO_ShouldMapUserIdAndMovieTitles() {
        UserEntity user = UserEntity.builder().id(10L).build();
        MovieEntity movie = MovieEntity.builder().title("Inception").build();

        WatchlistEntity entity = WatchlistEntity.builder()
                .user(user)
                .movies(List.of(movie))
                .build();

        WatchlistDTO dto = watchlistMapper.toWatchlistDTO(entity);

        assertEquals(10L, dto.getUserId());
        assertEquals("Inception", dto.getMovies().get(0));
    }

    @Test
    void toWatchlistEntity_ShouldMapUserIdToUserObject() {
        WatchlistDTO dto = new WatchlistDTO();
        dto.setUserId(5L);
        dto.setMovies(List.of("Interstellar"));

        WatchlistEntity entity = watchlistMapper.toWatchlistEntity(dto);

        assertNotNull(entity.getUser());
        assertEquals(5L, entity.getUser().getId());
    }
}