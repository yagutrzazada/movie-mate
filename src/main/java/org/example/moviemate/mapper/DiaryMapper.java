package org.example.moviemate.mapper;

import org.example.moviemate.dao.entity.DiaryEntity;
import org.example.moviemate.dao.entity.MovieEntity;
import org.example.moviemate.model.dto.DiaryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DiaryMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "movies", expression = "java(mapMoviesToTitles(diaryEntity.getMovies()))")
    DiaryDTO toDiaryDTO(DiaryEntity diaryEntity);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "movies", ignore = true)
    DiaryEntity toDiaryEntity(DiaryDTO diaryDTO);

    default List<String> mapMoviesToTitles(List<MovieEntity> movies) {
        if (movies == null) return null;
        return movies.stream()
                .map(MovieEntity::getTitle)
                .collect(Collectors.toList());
    }
}