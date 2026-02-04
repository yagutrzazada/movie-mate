package org.example.moviemate.mapper;

import org.example.moviemate.dao.entity.MovieEntity;
import org.example.moviemate.model.dto.MovieDTO;
import org.example.moviemate.model.enums.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MovieMapperTest {

    @Autowired
    private MovieMapper movieMapper;

    @Test
    void toMovieDTO_ShouldMapAllFields() {
        MovieEntity entity = MovieEntity.builder()
                .title("The Dark Knight")
                .genre(Genre.ACTION)
                .averageRating(9.0)
                .build();

        MovieDTO dto = movieMapper.toMovieDTO(entity);

        assertEquals("The Dark Knight", dto.getTitle());
        assertEquals(Genre.ACTION, dto.getGenre());
        assertEquals(9.0, dto.getAverageRating());
    }

    @Test
    void mapMoviesToTitles_ShouldReturnStringList() {
        MovieEntity m1 = MovieEntity.builder().title("Movie A").build();
        MovieEntity m2 = MovieEntity.builder().title("Movie B").build();

        List<String> titles = movieMapper.mapMoviesToTitles(List.of(m1, m2));

        assertEquals(2, titles.size());
        assertEquals("Movie A", titles.get(0));
    }
}