package org.example.moviemate.mapper;

import org.example.moviemate.dao.entity.MovieEntity;
import org.example.moviemate.model.dto.MovieDTO;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieEntity toMovieEntity(MovieDTO movieDTO);

    MovieDTO toMovieDTO(MovieEntity movieEntity);


    default List<String> mapMoviesToTitles(List<MovieEntity> movies) {
        if (movies != null) {
            return movies.stream()
                    .map(MovieEntity::getTitle)
                    .collect(Collectors.toList());
        }
        return null;
    }

    }

