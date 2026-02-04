package org.example.moviemate.mapper;

import org.example.moviemate.dao.entity.MovieEntity;
import org.example.moviemate.dao.entity.UserEntity;
import org.example.moviemate.dao.entity.WatchlistEntity;
import org.example.moviemate.model.dto.WatchlistDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface WatchlistMapper {

    @Mapping(target = "user", source = "userId")
    @Mapping(target = "movies", expression = "java(mapTitlesToMovies(watchlistDTO.getMovies()))")
    WatchlistEntity toWatchlistEntity(WatchlistDTO watchlistDTO);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "movies", expression = "java(mapMoviesToTitles(watchlistEntity.getMovies()))")
    WatchlistDTO toWatchlistDTO(WatchlistEntity watchlistEntity);

    default UserEntity map(Long userId) {
        if (userId != null) {
            UserEntity user = new UserEntity();
            user.setId(userId);
            return user;
        }
        return null;
    }

    //UserEntity->Long userId
    default Long map(UserEntity user) {
        return user != null ? user.getId() : null;
    }

    default List<String> mapMoviesToTitles(List<MovieEntity> movies) {
        if (movies != null) {
            return movies.stream()
                    .map(MovieEntity::getTitle)
                    .collect(Collectors.toList());
        }
        return null;
    }

    default List<MovieEntity> mapTitlesToMovies(List<String> titles) {
        if (titles != null) {
            return titles.stream()
                    .map(title -> {
                        MovieEntity movie = new MovieEntity();
                        movie.setTitle(title);
                        return movie;
                    })
                    .collect(Collectors.toList());
        }
        return null;
    }
}