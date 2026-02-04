package org.example.moviemate.mapper;

import org.example.moviemate.dao.entity.DiaryEntity;
import org.example.moviemate.dao.entity.MovieEntity;
import org.example.moviemate.dao.entity.UserEntity;
import org.example.moviemate.dao.entity.WatchlistEntity;
import org.example.moviemate.model.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "reviews", ignore = true)

    @Mapping(target = "watchlists", ignore = true)
    @Mapping(target = "diaries", ignore = true)
    UserEntity toUserEntity(UserDTO userDTO);


    @Mapping(target = "watchlist", expression = "java(mapWatchlistToTitles(userEntity.getWatchlists()))")
    @Mapping(target = "diary", expression = "java(mapDiaryToTitles(userEntity.getDiaries()))")
    UserDTO toUserDTO(UserEntity userEntity);


    default List<String> mapWatchlistToTitles(List<WatchlistEntity> watchlists) {
        if (watchlists == null) return null;
        return watchlists.stream()
                .flatMap(w -> w.getMovies().stream())
                .map(MovieEntity::getTitle)
                .collect(Collectors.toList());
    }

    default List<String> mapDiaryToTitles(List<DiaryEntity> diaries) {
        if (diaries == null) return null;
        return diaries.stream()
                .flatMap(d -> d.getMovies().stream())
                .map(MovieEntity::getTitle)
                .collect(Collectors.toList());
    }
}